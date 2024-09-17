package com.flipperdevices.ifrmvp.parser.presentation

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.core.IoCoroutineScope
import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileToSignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalKeyTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalNameAliasTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.UiPresetTable
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import com.flipperdevices.infrared.editor.encoding.InfraredRemoteEncoder.identifier
import com.flipperdevices.infrared.editor.model.InfraredRemote
import com.flipperdevices.infrared.editor.util.InfraredMapper
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import java.io.File
import kotlin.math.sign
import kotlin.time.measureTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

internal class FillerController(private val database: Database) : CoroutineScope by IoCoroutineScope() {

    suspend fun fillDatabase() {
        transaction(database) {
            // insert all categories
            CategoryTable.batchInsert(ParserPathResolver.categories) { categoriesFolder ->
                this[CategoryTable.folderName] = categoriesFolder.name
            }
            // Insert all brands
            ParserPathResolver.categories
                .map(File::nameWithoutExtension)
                .forEach { categoryFolder ->
                    val brands = ParserPathResolver.brands(categoryFolder)
                    val categoryId = CategoryTable.select(CategoryTable.id)
                        .where { CategoryTable.folderName eq categoryFolder }
                        .map { it[CategoryTable.id] }
                        .first()
                    BrandTable.batchInsert(brands) {
                        this[BrandTable.categoryId] = categoryId
                        this[BrandTable.folderName] = it.name
                    }
                    // Category meta
                    val categoryMeta = ParserPathResolver.categoryMeta(categoryFolder)
                    CategoryMetaTable.insert {
                        it[CategoryMetaTable.categoryId] = categoryId
                        it[CategoryMetaTable.displayName] = categoryMeta.manifest.displayName
                        it[CategoryMetaTable.singularDisplayName] = categoryMeta.manifest.singularDisplayName
                        it[CategoryMetaTable.iconPngBase64] = categoryMeta.iconPngBase64
                        it[CategoryMetaTable.iconSvgBase64] = categoryMeta.iconSvgBase64
                    }
                }
            // Infrared files
            ParserPathResolver.categories
                .map(File::nameWithoutExtension)
                .forEach { categoryFolder ->
                    val categoryId = CategoryTable.select(CategoryTable.id)
                        .where { CategoryTable.folderName eq categoryFolder }
                        .map { it[CategoryTable.id] }
                        .first()
                    val brands = ParserPathResolver.brands(categoryFolder)
                    brands.forEach { brand ->
                        val irFiles = ParserPathResolver.brandIfrFiles(
                            category = categoryFolder,
                            brand = brand.name
                        )
                        val brandId = BrandTable.select(BrandTable.id)
                            .where { BrandTable.folderName eq brand.name }
                            .andWhere { BrandTable.categoryId eq categoryId }
                            .map { it[BrandTable.id] }
                            .first()
                        InfraredFileTable.batchInsert(irFiles) {
                            this[InfraredFileTable.brandId] = brandId
                            this[InfraredFileTable.fileName] = it.name
                            this[InfraredFileTable.folderName] = it.parentFile.name
                            this[InfraredFileTable.signalCount] = InfraredMapper.parseRemotes(it).size
                        }
                        // Presets
                        val uiFiles = irFiles.mapNotNull { irFile ->
                            irFile.parentFile
                                .resolve("ui.json")
                                .takeIf(File::exists)
                        }
                        UiPresetTable.batchInsert(uiFiles) {
                            this[UiPresetTable.fileName] = it.name
                            this[UiPresetTable.infraredFileId] = InfraredFileTable
                                .select(InfraredFileTable.id)
                                .where { InfraredFileTable.brandId eq brandId }
                                .andWhere { InfraredFileTable.folderName eq it.parentFile.name }
                        }
                        // Signals
                        irFiles.forEach { irFile ->
                            val signals = irFile.readText()
                                .let(FlipperFileFormat::fromFileContent)
                                .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
                            SignalTable.batchInsert(signals, ignore = true) { remote ->
                                val parsedRemote = remote as? InfraredRemote.Parsed
                                val rawRemote = remote as? InfraredRemote.Raw
                                this[SignalTable.brandId] = brandId
                                this[SignalTable.type] = remote.type
                                this[SignalTable.protocol] = parsedRemote?.protocol
                                this[SignalTable.address] = parsedRemote?.address
                                this[SignalTable.command] = parsedRemote?.command
                                this[SignalTable.frequency] = rawRemote?.frequency
                                this[SignalTable.dutyCycle] = rawRemote?.dutyCycle
                                this[SignalTable.data] = rawRemote?.data
                                this[SignalTable.hash] = remote.identifier.hash
                            }
                            // ManyToMany file to signal references
                            val irFileId = InfraredFileTable
                                .select(InfraredFileTable.id)
                                .where { InfraredFileTable.brandId eq brandId }
                                .andWhere { InfraredFileTable.folderName eq irFile.parentFile.name }
                                .map { it[InfraredFileTable.id] }
                                .first()

                            val signalIds = signals.map {
                                val parsedRemote = it as? InfraredRemote.Parsed
                                val rawRemote = it as? InfraredRemote.Raw
                                SignalTable
                                    .select(SignalTable.id)
                                    .where { SignalTable.brandId eq brandId }
//                                    .andWhere { SignalTable.name eq it.name }
                                    .andWhere { SignalTable.type eq it.type }
                                    .andWhere { SignalTable.protocol eq parsedRemote?.protocol }
                                    .andWhere { SignalTable.address eq parsedRemote?.address }
                                    .andWhere { SignalTable.command eq parsedRemote?.command }
                                    .andWhere { SignalTable.frequency eq rawRemote?.frequency }
                                    .andWhere { SignalTable.dutyCycle eq rawRemote?.dutyCycle }
                                    .andWhere { SignalTable.data eq rawRemote?.data }
                                    .map { it[SignalTable.id] }
                                    .firstOrNull() ?: error(
                                    """
                                        The list is emoty for brand: ${brand.name} category: ${categoryFolder} file: ${irFile.name};
                                        name: ${it.name}
                                    """.trimIndent()
                                )
                            }

                            SignalNameAliasTable.batchInsert(signalIds.zip(signals), ignore = true) {
                                this[SignalNameAliasTable.signalName] = it.second.name
                                this[SignalNameAliasTable.signalId] = it.first.value
                            }

                            InfraredFileToSignalTable.batchInsert(signalIds) {
                                this[InfraredFileToSignalTable.infraredFileId] = irFileId
                                this[InfraredFileToSignalTable.signalId] = it
                            }
                            // Key Table
                            val irFileConfiguration = ParserPathResolver.irFileConfiguration(
                                category = categoryFolder,
                                brand = brand.name,
                                ifrFolderName = irFile.parentFile.name
                            )
                            if (irFileConfiguration.keyMap.entries.isEmpty()) {
                                error("Configuration file for ${irFile} is empty")
                            }
                            SignalKeyTable.batchInsert(irFileConfiguration.keyMap.entries) { (baseKey, keyIdentifier) ->
                                this[SignalKeyTable.infraredFileId] = irFileId
                                this[SignalKeyTable.deviceKey] = baseKey
                                when (keyIdentifier) {
                                    IfrKeyIdentifier.Empty -> {
                                        this[SignalKeyTable.type] = IfrKeyIdentifier.Empty.TYPE
                                    }

                                    is IfrKeyIdentifier.Name -> {
                                        this[SignalKeyTable.remoteKeyName] = keyIdentifier.name
                                        this[SignalKeyTable.type] = IfrKeyIdentifier.Sha256.TYPE
                                    }

                                    is IfrKeyIdentifier.Sha256 -> {
                                        this[SignalKeyTable.remoteKeyName] = keyIdentifier.name
                                        this[SignalKeyTable.type] = IfrKeyIdentifier.Sha256.TYPE
                                        this[SignalKeyTable.hash] = keyIdentifier.hash
                                    }
                                }

                                this[SignalKeyTable.signalId] = SignalTable
                                    .join(
                                        otherTable = InfraredFileToSignalTable,
                                        joinType = JoinType.LEFT,
                                        onColumn = SignalTable.id,
                                        otherColumn = InfraredFileToSignalTable.signalId
                                    )
                                    .join(
                                        otherTable = SignalNameAliasTable,
                                        joinType = JoinType.LEFT,
                                        onColumn = SignalTable.id,
                                        otherColumn = SignalNameAliasTable.signalId
                                    )
                                    .select(SignalTable.id)
                                    .where { SignalTable.brandId eq brandId }
                                    .andWhere { InfraredFileToSignalTable.infraredFileId eq irFileId }
                                    .apply {
                                        when (keyIdentifier) {
                                            IfrKeyIdentifier.Empty -> error("Identifying is not possible!")

                                            is IfrKeyIdentifier.Sha256 -> {
                                                andWhere { SignalTable.hash eq keyIdentifier.hash }
                                            }

                                            is IfrKeyIdentifier.Name -> {
                                                andWhere { SignalNameAliasTable.signalName eq keyIdentifier.name }
                                            }
                                        }
                                    }
                                    .map { it[SignalTable.id] }
                                    .also { assert(it.size == 1) }
                                    .firstOrNull() ?: error(
                                    """
                                        Could not resolve identifier ${keyIdentifier} for file ${irFile}
                                    """.trimIndent()
                                )
                            }
                        }
                    }
                }
        }
    }
}

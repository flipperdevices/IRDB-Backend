package com.flipperdevices.ifrmvp.parser.api

import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.UiPresetTable
import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.parser.model.RawIfrRemote
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class SignalTableApiImpl(
    private val database: Database,
) : SignalTableApi {

    private fun checkCategoryExists(id: Long) {
        val isExists = CategoryTable.selectAll()
            .where { CategoryTable.id eq id }
            .count() != 0L
        check(isExists) { "Category does not exists" }
    }

    private fun checkBrandExists(id: Long) {
        val isExists = BrandTable.selectAll()
            .where { BrandTable.id eq id }
            .count() != 0L
        check(isExists) { "Brand does not exists" }
    }

    private fun checkIrFileExists(id: Long) {
        val isExists = InfraredFileTable.selectAll()
            .where { InfraredFileTable.id eq id }
            .count() != 0L
        check(isExists) { "IrFile does not exists" }
    }

    override suspend fun addCategory(
        categoryFolderName: String,
    ): Long = transaction(database) {
        CategoryTable.selectAll()
            .where { CategoryTable.folderName eq categoryFolderName }
            .map { it[CategoryTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingCategoryId -> return@transaction existingCategoryId }
        CategoryTable.insertAndGetId { statement ->
            statement[CategoryTable.folderName] = categoryFolderName
        }.value
    }

    override suspend fun addBrand(
        categoryId: Long,
        displayName: String
    ): Long = transaction(database) {
        checkCategoryExists(categoryId)
        BrandTable.selectAll()
            .where { BrandTable.folderName eq displayName }
            .andWhere { BrandTable.categoryId eq categoryId }
            .map { it[BrandTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingBrandId -> return@transaction existingBrandId }

        BrandTable.insertAndGetId { statement ->
            statement[BrandTable.categoryId] = categoryId
            statement[BrandTable.folderName] = displayName
        }.value
    }

    override suspend fun addIrFile(
        fileName: String,
        categoryId: Long,
        brandId: Long,
        folderName: String
    ): Long = transaction(database) {
        checkCategoryExists(categoryId)
        checkBrandExists(brandId)
        InfraredFileTable.selectAll()
            .where { InfraredFileTable.fileName eq fileName }
            .andWhere { InfraredFileTable.brandId eq brandId }
            .map { it[InfraredFileTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingIrfFileId -> return@transaction existingIrfFileId }

        InfraredFileTable.insertAndGetId { statement ->
            statement[InfraredFileTable.brandId] = brandId
            statement[InfraredFileTable.fileName] = fileName
            statement[InfraredFileTable.folderName] = folderName
        }.value
    }

    override suspend fun addUiPreset(
        categoryId: Long,
        brandId: Long,
        irFileId: Long,
        fileName: String
    ): Unit = transaction(database) {
        checkCategoryExists(categoryId)
        checkBrandExists(brandId)
        checkIrFileExists(irFileId)

        UiPresetTable.selectAll()
            .where { UiPresetTable.infraredFileId eq irFileId }
            .andWhere { UiPresetTable.fileName eq fileName }
            .map { it[UiPresetTable.id] }
            .firstOrNull()
            ?.value
            ?.let { _ -> return@transaction }

        UiPresetTable.insert { statement ->
            statement[UiPresetTable.infraredFileId] = irFileId
            statement[UiPresetTable.fileName] = fileName
        }
    }

    override suspend fun addSignal(
        categoryId: Long,
        brandId: Long,
        irFileId: Long,
        remote: RawIfrRemote
    ): Long = transaction(database) {
        checkCategoryExists(categoryId)
        checkBrandExists(brandId)
        checkIrFileExists(irFileId)

        SignalTable.selectAll()
            .where { SignalTable.name eq remote.name }
            .andWhere { SignalTable.type eq remote.type }
            .andWhere { SignalTable.protocol eq remote.protocol }
            .andWhere { SignalTable.address eq remote.address }
            .andWhere { SignalTable.command eq remote.command }
            .andWhere { SignalTable.frequency eq remote.frequency }
            .andWhere { SignalTable.dutyCycle eq remote.dutyCycle }
            .andWhere { SignalTable.data eq remote.data }
            .map { it[SignalTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingIrfSignalId -> return@transaction existingIrfSignalId }

        SignalTable.insertAndGetId { statement ->
            statement[SignalTable.name] = remote.name
            statement[SignalTable.type] = remote.type
            statement[SignalTable.protocol] = remote.protocol
            statement[SignalTable.address] = remote.address
            statement[SignalTable.command] = remote.command
            statement[SignalTable.frequency] = remote.frequency
            statement[SignalTable.dutyCycle] = remote.dutyCycle
            statement[SignalTable.data] = remote.data
        }.value
    }

    override suspend fun addCategoryMeta(
        categoryId: Long,
        meta: CategoryMeta
    ): Unit = transaction(database) {
        checkCategoryExists(categoryId)
        CategoryMetaTable.selectAll()
            .where { CategoryMetaTable.categoryId eq categoryId }
            .map { it[CategoryMetaTable.id] }
            .firstOrNull()
            ?.value
            ?.let { _ -> return@transaction Unit }
        CategoryMetaTable.insert { statement ->
            statement[CategoryMetaTable.categoryId] = categoryId
            statement[CategoryMetaTable.displayName] = meta.manifest.displayName
            statement[CategoryMetaTable.singularDisplayName] = meta.manifest.singularDisplayName
            statement[CategoryMetaTable.iconPngBase64] = meta.iconPngBase64
            statement[CategoryMetaTable.iconSvgBase64] = meta.iconSvgBase64
        }
    }
}

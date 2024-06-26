package com.flipperdevices.ifrmvp.parser.api

import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalOrderTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.UiPresetTable
import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData
import com.flipperdevices.ifrmvp.parser.model.OrderModel
import com.flipperdevices.ifrmvp.parser.model.RawIfrRemote
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class SignalTableApiImpl(
    private val database: Database
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
        val isExists = IfrFileTable.selectAll()
            .where { IfrFileTable.id eq id }
            .count() != 0L
        check(isExists) { "IrFile does not exists" }
    }

    override suspend fun addCategory(
        categoryFolderName: String,
    ): Long = transaction(database) {
        CategoryTable.selectAll()
            .where { CategoryTable.categoryFolderName eq categoryFolderName }
            .map { it[CategoryTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingCategoryId -> return@transaction existingCategoryId }
        CategoryTable.insertAndGetId { statement ->
            statement[CategoryTable.categoryFolderName] = categoryFolderName
        }.value
    }

    override suspend fun addBrand(
        categoryId: Long,
        displayName: String
    ): Long = transaction(database) {
        checkCategoryExists(categoryId)
        BrandTable.selectAll()
            .where { BrandTable.displayName eq displayName }
            .andWhere { BrandTable.category eq categoryId }
            .map { it[BrandTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingBrandId -> return@transaction existingBrandId }

        BrandTable.insertAndGetId { statement ->
            statement[BrandTable.category] = categoryId
            statement[BrandTable.displayName] = displayName
        }.value
    }

    override suspend fun addIrFile(
        fileName: String,
        categoryId: Long,
        brandId: Long
    ): Long = transaction(database) {
        checkCategoryExists(categoryId)
        checkBrandExists(brandId)
        IfrFileTable.selectAll()
            .where { IfrFileTable.fileName eq fileName }
            .andWhere { IfrFileTable.categoryRef eq categoryId }
            .andWhere { IfrFileTable.brandRef eq brandId }
            .map { it[IfrFileTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingIrfFileId -> return@transaction existingIrfFileId }

        IfrFileTable.insertAndGetId { statement ->
            statement[IfrFileTable.categoryRef] = categoryId
            statement[IfrFileTable.brandRef] = brandId
            statement[IfrFileTable.fileName] = fileName
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
            .where { UiPresetTable.fileName eq fileName }
            .map { it[UiPresetTable.id] }
            .firstOrNull()
            ?.value
            ?.let { _ -> return@transaction }

        UiPresetTable.insert { statement ->
            statement[UiPresetTable.categoryRef] = categoryId
            statement[UiPresetTable.brandRef] = brandId
            statement[UiPresetTable.ifrFileRef] = irFileId
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
            .andWhere { SignalTable.categoryRef eq categoryId }
            .andWhere { SignalTable.ifrFileRef eq irFileId }
            .andWhere { SignalTable.brandRef eq brandId }
            .map { it[SignalTable.id] }
            .firstOrNull()
            ?.value
            ?.let { existingIrfSignalId -> return@transaction existingIrfSignalId }

        SignalTable.insertAndGetId { statement ->
            statement[SignalTable.categoryRef] = categoryId
            statement[SignalTable.brandRef] = brandId
            statement[SignalTable.ifrFileRef] = irFileId
            statement[SignalTable.name] = remote.name
            statement[SignalTable.type] = remote.type
            statement[SignalTable.protocol] = remote.protocol
            statement[SignalTable.address] = remote.address
            statement[SignalTable.command] = remote.command
            statement[SignalTable.frequency] = remote.frequency
            statement[SignalTable.dutyCycle] = remote.dutyCycle
            statement[SignalTable.data] = remote.data
            statement[SignalTable.hash] = remote.md5
        }.value
    }

    override suspend fun addCategoryMeta(
        categoryId: Long,
        meta: CategoryMeta
    ): Unit = transaction(database) {
        checkCategoryExists(categoryId)
        CategoryMetaTable.selectAll()
            .where { CategoryMetaTable.category eq categoryId }
            .map { it[CategoryMetaTable.id] }
            .firstOrNull()
            ?.value
            ?.let { _ -> return@transaction Unit }
        CategoryMetaTable.insert { statement ->
            statement[CategoryMetaTable.category] = categoryId
            statement[CategoryMetaTable.displayName] = meta.manifest.displayName
            statement[CategoryMetaTable.singularDisplayName] = meta.manifest.singularDisplayName
            statement[CategoryMetaTable.iconPngBase64] = meta.iconPngBase64
            statement[CategoryMetaTable.iconSvgBase64] = meta.iconSvgBase64
        }
    }

    override suspend fun addOrderModel(
        orderModel: OrderModel,
        ifrSignalId: Long,
        categoryId: Long,
        brandId: Long,
        ifrFileId: Long
    ): Unit = transaction(database) {
        SignalOrderTable.insert { statement ->
            statement[SignalOrderTable.categoryRef] = categoryId
            statement[SignalOrderTable.brandRef] = brandId
            statement[SignalOrderTable.ifrFileRef] = ifrFileId
            statement[SignalOrderTable.ifrSignalRef] = ifrSignalId
            statement[SignalOrderTable.dataType] = orderModel.data.type.name
            statement[SignalOrderTable.dataIconId] = when (orderModel.data) {
                is IconButtonData -> orderModel.data.iconId.name
                else -> null
            }
            statement[SignalOrderTable.dataText] = when (orderModel.data) {
                is TextButtonData -> orderModel.data.text
                else -> null
            }
        }
    }
}

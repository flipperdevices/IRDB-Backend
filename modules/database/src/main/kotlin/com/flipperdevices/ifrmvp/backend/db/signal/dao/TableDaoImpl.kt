package com.flipperdevices.ifrmvp.backend.db.signal.dao

import com.flipperdevices.ifrmvp.backend.db.signal.exception.TableDaoException
import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.model.BrandModel
import com.flipperdevices.ifrmvp.backend.model.CategoryManifest
import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

internal class TableDaoImpl(private val database: Database) : TableDao {
    override suspend fun getCategoryById(
        categoryId: Long
    ): DeviceCategory = newSuspendedTransaction(db = database) {
        CategoryTable
            .selectAll()
            .where { CategoryTable.id eq categoryId }
            .limit(1)
            .map { r ->
                DeviceCategory(
                    id = r[CategoryTable.id].value,
                    folderName = r[CategoryTable.folderName],
                    meta = CategoryMetaTable.selectAll()
                        .where { CategoryMetaTable.categoryId eq categoryId }
                        .limit(1)
                        .map {
                            CategoryMeta(
                                iconPngBase64 = it[CategoryMetaTable.iconPngBase64],
                                iconSvgBase64 = it[CategoryMetaTable.iconSvgBase64],
                                manifest = CategoryManifest(
                                    displayName = it[CategoryMetaTable.displayName],
                                    singularDisplayName = it[CategoryMetaTable.singularDisplayName],
                                )
                            )
                        }.firstOrNull() ?: throw TableDaoException.CategoryMeta(categoryId)
                )
            }.firstOrNull() ?: throw TableDaoException.CategoryNotFound(categoryId)
    }

    override suspend fun getBrandById(
        brandId: Long
    ): BrandModel = newSuspendedTransaction(db = database) {
        BrandTable
            .selectAll()
            .where { BrandTable.id eq brandId }
            .limit(1)
            .map {
                BrandModel(
                    id = it[BrandTable.id].value,
                    folderName = it[BrandTable.folderName],
                    categoryId = it[BrandTable.categoryId].value
                )
            }.firstOrNull() ?: throw TableDaoException.BrandNotFound(brandId)
    }

    override suspend fun ifrFileById(
        irFileId: Long
    ): IfrFileModel = newSuspendedTransaction(db = database) {
        InfraredFileTable
            .selectAll()
            .where { InfraredFileTable.id eq irFileId }
            .limit(1)
            .map {
                IfrFileModel(
                    id = it[InfraredFileTable.id].value,
                    brandId = it[InfraredFileTable.brandId].value,
                    fileName = it[InfraredFileTable.fileName],
                    folderName = it[InfraredFileTable.folderName]
                )
            }.firstOrNull() ?: throw TableDaoException.IrFileNotFound(irFileId)
    }
}

package com.flipperdevices.ifrmvp.backend.route.key.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

interface KeyRouteRepository {
    suspend fun getIfrFile(ifrFileId: Long): File
}

class KeyRouteRepositoryImpl(private val database: Database) : KeyRouteRepository {
    override suspend fun getIfrFile(ifrFileId: Long): File {
        val ifrFileModel = transaction(database) {
            IfrFileTable.selectAll()
                .where { IfrFileTable.id eq ifrFileId }
                .map {
                    IfrFileModel(
                        id = it[IfrFileTable.id].value,
                        categoryId = it[IfrFileTable.categoryId].value,
                        brandId = it[IfrFileTable.brandId].value,
                        fileName = it[IfrFileTable.fileName]
                    )
                }
                .firstOrNull()
                ?: error("Ir file with id $ifrFileId not found!")
        }
        val categoryFolderName = transaction(database) {
            CategoryTable.select(CategoryTable.folderName)
                .where { CategoryTable.id eq ifrFileModel.categoryId }
                .map { it[CategoryTable.folderName] }
                .firstOrNull()
                ?: error("Category with id ${ifrFileModel.categoryId} not found!")
        }
        val brandFolderName = transaction(database) {
            BrandTable.select(BrandTable.displayName)
                .where { BrandTable.id eq ifrFileModel.brandId }
                .map { it[BrandTable.displayName] }
                .firstOrNull()
                ?: error("Brand with id ${ifrFileModel.brandId} not found!")
        }
        println("Category :$categoryFolderName brand: $brandFolderName ifr: ${ifrFileModel.fileName}")
        val file = ParserPathResolver.ifrFile(
            category = categoryFolderName,
            brand = brandFolderName,
            ifrFolderName = ifrFileModel.fileName.replaceFirst(".ir", "") // todo
        )
        return file
    }
}

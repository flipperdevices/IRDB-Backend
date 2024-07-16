package com.flipperdevices.ifrmvp.backend.route.brands.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.model.BrandModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class BrandsRepositoryImpl(private val database: Database) : BrandsRepository {
    override suspend fun getBrands(categoryId: Long): List<BrandModel> {
        return transaction(database) {
            BrandTable.selectAll()
                .where { BrandTable.categoryId eq categoryId }
                .map {
                    BrandModel(
                        id = it[BrandTable.id].value,
                        folderName = it[BrandTable.folderName],
                        categoryId = it[BrandTable.categoryId].value,
                    )
                }
        }
    }
}

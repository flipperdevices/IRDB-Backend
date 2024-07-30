package com.flipperdevices.ifrmvp.backend.route.brands.data

import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.model.BrandModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upperCase

internal class BrandsRepositoryImpl(
    private val database: Database,
    private val tableDao: TableDao
) : BrandsRepository {
    override suspend fun getBrands(categoryId: Long, query: String): List<BrandModel> {
        check(tableDao.getCategoryById(categoryId).id == categoryId)
        return transaction(database) {
            BrandTable.selectAll()
                .where { BrandTable.categoryId eq categoryId }
                .apply {
                    if (query.isEmpty()) this
                    else andWhere { BrandTable.folderName.upperCase().like("%${query.uppercase()}%") }
                }
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

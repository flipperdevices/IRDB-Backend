package com.flipperdevices.ifrmvp.backend.route.infrareds.data

import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class InfraredsRepository(
    private val database: Database,
    private val tableDao: TableDao
) {
    suspend fun fetchInfrareds(brandId: Long): List<IfrFileModel> {
        tableDao.getBrandById(brandId)
        return transaction(database) {
            InfraredFileTable.selectAll()
                .where { InfraredFileTable.brandId eq brandId }
                .map {
                    IfrFileModel(
                        id = it[InfraredFileTable.id].value,
                        brandId = it[InfraredFileTable.brandId].value,
                        fileName = it[InfraredFileTable.fileName],
                        folderName = it[InfraredFileTable.folderName],
                    )
                }
        }
    }
}
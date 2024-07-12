package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

internal class IrFileRepository {

    fun getNextIrFile(signalRequestModel: SignalRequestModel): IfrFileModel? {
        return IfrFileTable
            .selectAll()
            .andWhere { IfrFileTable.id.notInList(signalRequestModel.failedResults.map { it.ifrFileId }) }
            .andWhere { IfrFileTable.categoryId eq signalRequestModel.categoryId }
            .andWhere { IfrFileTable.brandId eq signalRequestModel.brandId }
            .limit(1)
            .map {
                IfrFileModel(
                    id = it[IfrFileTable.id].value,
                    categoryId = it[IfrFileTable.categoryId].value,
                    brandId = it[IfrFileTable.brandId].value,
                    fileName = it[IfrFileTable.fileName]
                )
            }.firstOrNull()
    }

    fun getMostSuccessfulIfrFile(signalRequestModel: SignalRequestModel): IfrFileModel? {
        val id = signalRequestModel.successResults
            .groupBy { it.ifrFileId }
            .maxByOrNull { it.value.size }
            ?.key
        return IfrFileTable
            .selectAll()
            .where { IfrFileTable.id eq id }
            .map {
                IfrFileModel(
                    id = it[IfrFileTable.id].value,
                    categoryId = it[IfrFileTable.categoryId].value,
                    brandId = it[IfrFileTable.brandId].value,
                    fileName = it[IfrFileTable.fileName]
                )
            }.firstOrNull()
    }
}

package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

internal class IrFileRepository {

    fun getIrFile(signalRequestModel: SignalRequestModel): IfrFileModel? {
        return IfrFileTable
            .selectAll()
            .andWhere { IfrFileTable.id.notInList(signalRequestModel.failedResults.map { it.ifrFileId }) }
            .andWhere { IfrFileTable.categoryRef eq signalRequestModel.categoryId }
            .andWhere { IfrFileTable.brandRef eq signalRequestModel.brandId }
            .limit(1)
            .map {
                IfrFileModel(
                    id = it[IfrFileTable.id].value,
                    categoryId = it[IfrFileTable.categoryRef].value,
                    brandId = it[IfrFileTable.brandRef].value
                )
            }.firstOrNull()
    }
}

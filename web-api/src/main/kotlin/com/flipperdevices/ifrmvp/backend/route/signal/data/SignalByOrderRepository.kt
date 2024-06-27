package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalOrderTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalOrderModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.mapping.SignalModelMapper.toSignalModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

internal class SignalByOrderRepository {

    fun getSignalByOrder(
        signalRequestModel: SignalRequestModel,
        ifrFile: IfrFileModel,
        orderCount: Long,
        successCount: Long
    ): SignalResponseModel {
        if (successCount == orderCount) {
            return SignalResponseModel(ifrFileModel = ifrFile)
        }
        val signalOrderModel = SignalOrderTable
            .selectAll()
            .andWhere { SignalOrderTable.ifrSignalRef eq ifrFile.id }
            .andWhere {
                SignalOrderTable.ifrSignalRef.notInList(
                    signalRequestModel.successResults.map { it.signalId }
                )
            }
            .map { signalOrderResultRow ->
                val signalId = signalOrderResultRow[SignalOrderTable.ifrSignalRef].value
                SignalOrderModel(
//                    id = signalOrderResultRow[SignalOrderTable.id].value,
                    brandId = signalOrderResultRow[SignalOrderTable.brandRef].value,
                    categoryId = signalOrderResultRow[SignalOrderTable.categoryRef].value,
                    ifrFile = ifrFile,
                    dataType = signalOrderResultRow[SignalOrderTable.dataType],
                    dataIconId = signalOrderResultRow[SignalOrderTable.dataIconId],
                    dataText = signalOrderResultRow[SignalOrderTable.dataText],
                    signalModel = SignalTable
                        .selectAll()
                        .where { SignalTable.id eq signalId }
                        .map { signalResultRow -> signalResultRow.toSignalModel() }
                        .first()
                )
            }.firstOrNull()
        // All signals passed
        if (signalOrderModel == null) return SignalResponseModel(ifrFileModel = ifrFile)
        return SignalResponseModel(signalOrderModel = signalOrderModel)
    }
}

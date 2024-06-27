package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalOrderTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.mapping.SignalModelMapper.toSignalModel
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
        val signalResponse = SignalOrderTable
            .selectAll()
            .andWhere { SignalOrderTable.ifrSignalRef eq ifrFile.id }
            .andWhere {
                SignalOrderTable.ifrSignalRef.notInList(
                    signalRequestModel.successResults.map { it.signalId }
                )
            }
            .map { signalOrderResultRow ->
                val signalId = signalOrderResultRow[SignalOrderTable.ifrSignalRef].value
                SignalResponse(
                    data = SignalResponse.Data(
                        type = signalOrderResultRow[SignalOrderTable.dataType],
                        iconId = signalOrderResultRow[SignalOrderTable.dataIconId],
                        text = signalOrderResultRow[SignalOrderTable.dataText],
                    ),
                    signalModel = SignalTable
                        .selectAll()
                        .where { SignalTable.id eq signalId }
                        .map { signalResultRow -> signalResultRow.toSignalModel() }
                        .first()
                )
            }.firstOrNull()
        // All signals passed
        if (signalResponse == null) return SignalResponseModel(ifrFileModel = ifrFile)
        return SignalResponseModel(signalResponse = signalResponse)
    }
}

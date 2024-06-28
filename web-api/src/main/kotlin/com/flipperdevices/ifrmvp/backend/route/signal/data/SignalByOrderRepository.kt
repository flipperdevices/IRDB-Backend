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
        successCount: Long,
        categorySingularDisplayName: String
    ): SignalResponseModel {
        if (successCount == orderCount) {
            return SignalResponseModel(ifrFileModel = ifrFile)
        }
        println(
            "SignalOrderTable size: ${SignalOrderTable.select(
                SignalOrderTable.id
            ).where { SignalOrderTable.ifrFileId eq ifrFile.id }.count()}"
        )
        val signalResponse = SignalOrderTable
            .selectAll()
            .where { SignalOrderTable.ifrFileId eq ifrFile.id }
            .andWhere {
                SignalOrderTable.ifrSignalId.notInList(signalRequestModel.successResults.map { it.signalId })
            }
            .map { signalOrderResultRow ->
                val signalId = signalOrderResultRow[SignalOrderTable.ifrSignalId].value
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
                        .first(),
                    message = signalOrderResultRow[SignalOrderTable.message].format(categorySingularDisplayName),
                    categoryName = categorySingularDisplayName
                )
            }.firstOrNull()
        // All signals passed
        if (signalResponse == null) {
            return SignalResponseModel(ifrFileModel = ifrFile)
        }
        return SignalResponseModel(signalResponse = signalResponse)
    }
}

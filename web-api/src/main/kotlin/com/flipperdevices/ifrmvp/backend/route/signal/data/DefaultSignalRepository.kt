package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.mapping.SignalModelMapper.toSignalModel
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

internal class DefaultSignalRepository {

    fun getDefaultSignal(
        successCount: Long,
        ifrFile: IfrFileModel,
        signalRequestModel: SignalRequestModel,
    ): SignalResponseModel {
        return if (successCount >= 4) {
            SignalResponseModel(ifrFileModel = ifrFile)
        } else {
            val signalModel = SignalTable
                .selectAll()
                .andWhere { SignalTable.ifrFileRef eq ifrFile.id }
                .andWhere { SignalTable.id.notInList(signalRequestModel.successResults.map { it.signalId }) }
                .limit(1)
                .map { signalResultRow -> signalResultRow.toSignalModel() }
                .firstOrNull()
            if (signalModel == null) {
                SignalResponseModel(ifrFileModel = ifrFile)
            } else {
                SignalResponseModel(
                    signalResponse = SignalResponse(
                        signalModel = signalModel,
                        data = SignalResponse.Data(
                            type = ButtonData.ButtonType.TEXT.name,
                            iconId = null,
                            text = signalModel.name
                        )
                    )
                )
            }
        }
    }
}

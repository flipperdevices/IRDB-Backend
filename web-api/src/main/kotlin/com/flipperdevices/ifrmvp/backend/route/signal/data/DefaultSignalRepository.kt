package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalOrderModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.mapping.SignalModelMapper.toSignalModel
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
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
                    signalOrderModel = SignalOrderModel(
                        categoryId = ifrFile.categoryId,
                        brandId = ifrFile.brandId,
                        ifrFile = ifrFile,
                        signalModel = signalModel,
                        dataType = ButtonData.ButtonType.TEXT.name,
                        dataIconId = null,
                        dataText = signalModel.name
                    )
                )
            }
        }
    }
}

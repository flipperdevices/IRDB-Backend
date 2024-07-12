package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.mapping.SignalModelMapper.toSignalModel
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll

internal class DefaultSignalRepository {

    private fun getSignal(
        ifrFileId: Long,
        signalRequestModel: SignalRequestModel
    ): SignalModel? {
        val count = signalRequestModel.successResults.count { it.ifrFileId == ifrFileId }
        return SignalTable
            .selectAll()
            .andWhere { SignalTable.ifrFileId eq ifrFileId }
            .andWhere { SignalTable.id.notInList(signalRequestModel.successResults.map { it.signalId }) }
            .apply {
                when {
                    count == 0 -> andWhere {
                        SignalTable.name.eq("power")
                            .or(SignalTable.name.eq("on"))
                            .or(SignalTable.name.like("%power%"))
                            .or(SignalTable.name.like("%on%"))
                    }

                    count >= 1 -> andWhere {
                        SignalTable.name.neq("power")
                            .or(SignalTable.name.neq("on"))
                            .or(SignalTable.name.notLike("%power%"))
                            .or(SignalTable.name.notLike("%on%"))
                    }

                    else -> this
                }
            }
            .limit(1)
            .map { signalResultRow -> signalResultRow.toSignalModel() }
            .firstOrNull()
    }

    fun getDefaultSignal(
        ifrFile: IfrFileModel,
        signalRequestModel: SignalRequestModel,
        categorySingularDisplayName: String,
        successCount: Long,
    ): SignalResponseModel {
        val signalModel = getSignal(ifrFile.id, signalRequestModel)
        return when {
            signalModel == null && successCount == 0L -> {
                SignalResponseModel()
            }

            signalModel == null -> {
                SignalResponseModel(ifrFileModel = ifrFile)
            }

            else -> {
                SignalResponseModel(
                    signalResponse = SignalResponse(
                        signalModel = signalModel,
                        data = SignalResponse.Data(
                            type = ButtonData.ButtonType.TEXT.name,
                            iconId = null,
                            text = signalModel.name
                        ),
                        categoryName = categorySingularDisplayName,
                        message = "Does $categorySingularDisplayName respond to button?"
                    )
                )
            }
        }
    }
}

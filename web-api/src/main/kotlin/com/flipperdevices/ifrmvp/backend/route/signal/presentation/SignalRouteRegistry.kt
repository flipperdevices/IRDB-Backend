package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalOrderTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.ifrmvp.backend.model.SignalOrderModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class SignalRouteRegistry(private val database: Database) : RouteRegistry {

    private fun ResultRow.toSignalModel(): SignalModel {
        val signalResultRow = this
        return SignalModel(
            id = signalResultRow[SignalTable.id].value,
            irFileId = signalResultRow[SignalTable.ifrFileRef].value,
            brandId = signalResultRow[SignalTable.brandRef].value,
            categoryId = signalResultRow[SignalTable.categoryRef].value,
            name = signalResultRow[SignalTable.name],
            type = signalResultRow[SignalTable.type],
            protocol = signalResultRow[SignalTable.protocol],
            address = signalResultRow[SignalTable.address],
            command = signalResultRow[SignalTable.command],
            frequency = signalResultRow[SignalTable.frequency],
            dutyCycle = signalResultRow[SignalTable.dutyCycle],
            data = signalResultRow[SignalTable.data],
            hash = signalResultRow[SignalTable.hash]
        )
    }

    private fun getSignalByOrder(
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

    private fun getSignal(signalRequestModel: SignalRequestModel): SignalResponseModel {
        return transaction(database) {
            // Looking for ifr file id with non-failed results
            val ifrFile = IfrFileTable
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
            // If file not found then we don't have signals
            if (ifrFile == null) return@transaction SignalResponseModel()

            val orderCount = SignalOrderTable
                .select(SignalOrderTable.id)
                .where { SignalOrderTable.ifrFileRef eq ifrFile.id }
                .count()
            println("Orders count: $orderCount")

            val successCount = signalRequestModel
                .successResults
                .count { it.ifrFileId == ifrFile.id }
                .toLong()

            println("Success count: $successCount")
            println("IfrFileId: ${ifrFile.id} category: ${ifrFile.categoryId} brand: ${ifrFile.brandId}")

            // If orders empty, getting just by signal
            // If orders not empty, getting by orders
            return@transaction if (orderCount == 0L) {
                if (successCount >= 4) {
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
            } else {
                getSignalByOrder(
                    signalRequestModel = signalRequestModel,
                    ifrFile = ifrFile,
                    orderCount = orderCount,
                    successCount = successCount,
                )
            }
        }
    }

    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                val signalRequestModel = context.receive<SignalRequestModel>()
                val signalResponseModel = getSignal(signalRequestModel)
                context.respond(signalResponseModel)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

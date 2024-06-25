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
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class SignalRouteRegistry(private val database: Database) : RouteRegistry {

    private fun getSignal(signalRequestModel: SignalRequestModel): SignalResponseModel {
        return transaction(database) {
            val ifrFile = IfrFileTable
                .selectAll()
                .andWhere { IfrFileTable.id.notInList(signalRequestModel.failedResults.map { it.ifrFileId }) }
                .limit(1)
                .map {
                    IfrFileModel(
                        id = it[IfrFileTable.id].value,
                        categoryId = it[IfrFileTable.categoryRef].value,
                        brandId = it[IfrFileTable.brandRef].value
                    )
                }.firstOrNull()
            if (ifrFile == null) return@transaction SignalResponseModel()
            val orderCount = SignalOrderTable
                .select(SignalOrderTable.order)
                .where { SignalOrderTable.ifrFileRef eq ifrFile.id }
                .count()
            val successCount = signalRequestModel
                .successResults
                .count { it.ifrFileId == ifrFile.id }
                .toLong()
            if (successCount == orderCount) {
                return@transaction SignalResponseModel(ifrFileModel = ifrFile)
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
                        id = signalOrderResultRow[SignalOrderTable.id].value,
                        brandId = signalOrderResultRow[SignalOrderTable.brandRef].value,
                        categoryId = signalOrderResultRow[SignalOrderTable.categoryRef].value,
                        ifrFile = ifrFile,
                        order = signalOrderResultRow[SignalOrderTable.order],
                        dataTye = signalOrderResultRow[SignalOrderTable.dataType],
                        dataIconId = signalOrderResultRow[SignalOrderTable.dataIconId],
                        dateText = signalOrderResultRow[SignalOrderTable.dataText],
                        signalModel = SignalTable
                            .selectAll()
                            .where { SignalTable.id eq signalId }
                            .map { signalResultRow ->
                                SignalModel(
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
                            }.first()
                    )
                }.firstOrNull()
            // All signals passed
            if (signalOrderModel == null) return@transaction SignalResponseModel(ifrFileModel = ifrFile)
            return@transaction SignalResponseModel(signalOrderModel = signalOrderModel)
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

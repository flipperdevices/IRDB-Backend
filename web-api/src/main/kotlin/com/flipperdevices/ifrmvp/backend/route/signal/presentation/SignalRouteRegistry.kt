package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.SignalButtonIconType
import com.flipperdevices.ifrmvp.backend.model.SignalButtonInfo
import com.flipperdevices.ifrmvp.backend.model.SignalModel
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

    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                val signalRequestModel = context.receive<SignalRequestModel>()

                val signalModels = transaction(database) {
                    SignalTable.selectAll()
                        .where { SignalTable.brandRef eq signalRequestModel.brandId }
                        .andWhere { SignalTable.categoryRef eq signalRequestModel.categoryId }
                        .andWhere { SignalTable.id.notInList(signalRequestModel.failedResults.map { it.signalId }) }
                        .andWhere { SignalTable.id.notInList(signalRequestModel.successResults.map { it.signalId }) }
                        .andWhere {
                            SignalTable.ifrFileRef.notInList(
                                signalRequestModel.failedResults.map { it.ifrFileId }
                            )
                        }
                        .map {
                            SignalModel(
                                id = it[SignalTable.id].value,
                                irFileId = it[SignalTable.ifrFileRef].value,
                                brandId = it[SignalTable.brandRef].value,
                                categoryId = it[SignalTable.categoryRef].value,
                                name = it[SignalTable.name],
                                type = it[SignalTable.type],
                                protocol = it[SignalTable.protocol],
                                address = it[SignalTable.address],
                                command = it[SignalTable.command],
                                frequency = it[SignalTable.frequency],
                                dutyCycle = it[SignalTable.dutyCycle],
                                data = it[SignalTable.data]
                            )
                        }
                }
                val distinctIrFilesAmount = signalModels.distinctBy(SignalModel::irFileId).size
                println("signalModelsCount: ${signalModels.size}; distinctIrFilesAmount: $distinctIrFilesAmount")

                val responseModel = SignalResponseModel(
                    signalModel = signalModels.firstOrNull(),
                    hasNext = distinctIrFilesAmount > 1,
                    buttonInfo = SignalButtonInfo(
                        backgroundColor = 0xFFF63F3F,
                        tintColor = 0xFFFFFFFF,
                        iconType = SignalButtonIconType.POWER
                    )
                )
                context.respond(responseModel)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.data.CounterRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.DefaultSignalRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.IrFileRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.SignalByOrderRepository
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

internal class SignalRouteRegistry(
    private val database: Database,
    private val irFileRepository: IrFileRepository,
    private val counterRepository: CounterRepository,
    private val defaultSignalRepository: DefaultSignalRepository,
    private val signalByOrderRepository: SignalByOrderRepository
) : RouteRegistry {

    private fun getSignal(signalRequestModel: SignalRequestModel): SignalResponseModel {
        return transaction(database) {
            // Looking for ifr file id with non-failed results
            // If file not found then we don't have signals
            val ifrFile = irFileRepository
                .getIrFile(signalRequestModel)
                ?: return@transaction SignalResponseModel()

            val orderCount = counterRepository.countOrders(ifrFile.id)
            val successCount = signalRequestModel
                .successResults
                .count { it.ifrFileId == ifrFile.id }
                .toLong()

            println("Success count: $successCount; Orders count: $orderCount")
            println("IfrFileId: ${ifrFile.id} category: ${ifrFile.categoryId} brand: ${ifrFile.brandId}")

            // If orders empty, getting just by signal
            // If orders not empty, getting by orders
            return@transaction if (orderCount == 0L) {
                defaultSignalRepository.getDefaultSignal(
                    successCount = successCount,
                    ifrFile = ifrFile,
                    signalRequestModel = signalRequestModel
                )
            } else {
                signalByOrderRepository.getSignalByOrder(
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

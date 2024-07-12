package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
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

    private fun getCategorySingularDisplayName(categoryId: Long): String {
        return CategoryMetaTable
            .select(CategoryMetaTable.singularDisplayName)
            .where { CategoryMetaTable.categoryId eq categoryId }
            .limit(1)
            .map { it[CategoryMetaTable.singularDisplayName] }
            .firstOrNull()
            ?: error("Could not find category $categoryId")
    }

    private fun getSignal(signalRequestModel: SignalRequestModel): SignalResponseModel {
        return transaction(database) {
            // Looking for ifr file id with non-failed results
            // If file not found then we don't have signals
            val ifrFile = irFileRepository.getNextIrFile(signalRequestModel)
            if (ifrFile == null) {
                val mostSuccessIfrFile = irFileRepository.getMostSuccessfulIfrFile(signalRequestModel)
                return@transaction SignalResponseModel(ifrFileModel = mostSuccessIfrFile)
            }

            val orderCount = counterRepository.countOrders(ifrFile.id)

            val successCount = signalRequestModel
                .successResults
                .count { it.ifrFileId == ifrFile.id }
                .toLong()

            val categorySingularDisplayName = getCategorySingularDisplayName(signalRequestModel.categoryId)

            println("Success count: $successCount; Orders count: $orderCount")
            println("IfrFileId: ${ifrFile.id} category: ${ifrFile.categoryId} brand: ${ifrFile.brandId}")

            return@transaction when {
                orderCount == 1L && successCount >= 1 -> {
                    SignalResponseModel(ifrFileModel = ifrFile)
                }

                orderCount == 0L && successCount >= MAX_SUCCESS_ROW -> {
                    SignalResponseModel(ifrFileModel = ifrFile)
                }

                orderCount == 0L -> {
                    defaultSignalRepository.getDefaultSignal(
                        ifrFile = ifrFile,
                        signalRequestModel = signalRequestModel,
                        categorySingularDisplayName = categorySingularDisplayName,
                        successCount = successCount
                    )
                }

                else -> {
                    signalByOrderRepository.getSignalByOrder(
                        signalRequestModel = signalRequestModel,
                        ifrFile = ifrFile,
                        orderCount = orderCount,
                        successCount = successCount,
                        categorySingularDisplayName = categorySingularDisplayName,
                    )
                }
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

    companion object {
        private const val MAX_SUCCESS_ROW = 4
    }
}

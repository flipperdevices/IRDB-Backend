package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.exception.TableDaoException
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.backend.route.signal.data.IncludedFilesRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.OrderRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.SignalRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.model.IncludedFile
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database

@Suppress("UnusedPrivateProperty")
internal class SignalRouteRegistry(
    private val database: Database,
    private val tableDao: TableDao,
) : RouteRegistry {
    private val includedFilesRepository = IncludedFilesRepository(database)
    private val signalRepository = SignalRepository(database)
    private val orderRepository = OrderRepository(database)


    private suspend fun getNextSignal(
        signalRequestModel: SignalRequestModel,
        categoryType: CategoryType,
        includedFiles: List<IncludedFile>,
        category: DeviceCategory,
        recursionLevel: Int = 0
    ): SignalResponseModel? {
        val orderModel = orderRepository.findOrder(
            signalRequestModel = signalRequestModel,
            categoryType = categoryType,
            recursionLevel = recursionLevel
        ) ?: run {
            println("Order is null for recursionLevel: $recursionLevel $signalRequestModel")
            return null
        }

        val signalModel = signalRepository.getSignalModel(
            signalRequestModel = signalRequestModel,
            order = orderModel,
        )
        // For some orders signal may be null
        // But it may be not null for next orders
        if (signalModel == null) {
            println("Signal model is null for $orderModel")
            return getNextSignal(
                signalRequestModel = signalRequestModel,
                categoryType = categoryType,
                includedFiles = includedFiles,
                category = category,
                recursionLevel = recursionLevel + 1
            )
        }
        return SignalResponseModel(
            signalResponse = SignalResponse(
                signalModel = signalModel,
                message = orderModel.message,
                categoryName = category.meta.manifest.singularDisplayName,
                data = orderModel.data
            )
        )
    }

    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                @Suppress("UnusedPrivateProperty")
                val signalRequestModel = context.receive<SignalRequestModel>()
                println("signalRequestModel: ${signalRequestModel}")

                val brand = tableDao.getBrandById(signalRequestModel.brandId)
                val category = tableDao.getCategoryById(brand.categoryId)
                val categoryType = CategoryType
                    .entries
                    .firstOrNull { it.folderName == category.folderName }
                    ?: throw TableDaoException.CategoryNotFound(category.id)


                val includedFiles = includedFilesRepository.findIncludedFiles(signalRequestModel)


                when {
                    includedFiles.isEmpty() -> {
                        println("#root includedInfraredFilesCount is empty!")
                        val irFileModel = includedFilesRepository
                            .findFallbackFile(signalRequestModel)
                            ?.fileId
                            ?.let { id -> tableDao.ifrFileById(id) }
                        if (irFileModel == null) {
                            context.respond(HttpStatusCode.NoContent)
                        } else {
                            context.respond(SignalResponseModel(ifrFileModel = irFileModel))
                        }
                    }

                    includedFiles.size == 1 -> {
                        println("#root found exact one infrared file")
                        val irFileModel = tableDao.ifrFileById(includedFiles.first().fileId)
                        val response = SignalResponseModel(ifrFileModel = irFileModel)
                        context.respond(response)
                    }

                    else -> {
                        val signal = getNextSignal(
                            signalRequestModel = signalRequestModel,
                            categoryType = categoryType,
                            includedFiles = includedFiles,
                            category = category
                        )

                        if (signal != null) {
                            println("#root found multiple infrared files: ${includedFiles.size}")
                            context.respond(signal)
                        } else {
                            println("#root could not find signal model. Giving fallback file")

                            val irFileModel = includedFilesRepository
                                .findFallbackFile(signalRequestModel)
                                ?.fileId
                                ?.let { id -> tableDao.ifrFileById(id) }

                            if (irFileModel == null) {
                                context.respond(HttpStatusCode.NoContent)
                            } else {
                                context.respond(SignalResponseModel(ifrFileModel = irFileModel))
                            }
                        }

                    }
                }
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

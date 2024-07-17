package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileToSignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.generator.config.category.api.AllCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryType
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyKeyNamesProvider
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

@Suppress("UnusedPrivateProperty")
internal class SignalRouteRegistry(
    private val database: Database,
    private val tableDao: TableDao,
) : RouteRegistry {
    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                @Suppress("UnusedPrivateProperty")
                val signalRequestModel = context.receive<SignalRequestModel>()
                // Index of successful results
                val index = signalRequestModel.successResults.size

                val brand = tableDao.getBrandById(signalRequestModel.brandId)
                val category = tableDao.getCategoryById(brand.categoryId)
                val categoryType = CategoryType
                    .entries
                    .firstOrNull { it.folderName == category.folderName }
                    ?: error("Could not find category with folderName ${category.folderName}")
                val order = AllCategoryConfigGenerator
                    .generate(categoryType)
                    .orders
                    .getOrNull(index)
                    ?: TODO()
                val keyNames = AnyKeyNamesProvider.getKeyNames(order.key)

                // Keep only those files, in which signals have been successfully passed
                val includedFileIds = InfraredFileToSignalTable
                    .select(InfraredFileToSignalTable.infraredFileId)
                    .groupBy(InfraredFileToSignalTable.infraredFileId)
                    .where {
                        InfraredFileToSignalTable
                            .signalId
                            .inList(
                                signalRequestModel.successResults.map(SignalRequestModel.SignalResultData::signalId)
                            )
                    }

                when (includedFileIds.count()) {
                    0L -> {
                        TODO()
                    }

                    1L -> {
                        val infraredFileId = includedFileIds
                            .map { it[InfraredFileToSignalTable.infraredFileId] }
                            .first()
                            .value
                        val response = SignalResponseModel(ifrFileModel = tableDao.ifrFileById(infraredFileId))
                        context.respond(response)
                    }

                    else -> {
                        val includedSignalIds = InfraredFileToSignalTable
                            .select(InfraredFileToSignalTable.signalId)
                            .groupBy(InfraredFileToSignalTable.signalId)
                            .where {
                                InfraredFileToSignalTable
                                    .infraredFileId
                                    .inSubQuery(includedFileIds)
                            }

                        val signalModel = SignalTable.selectAll()
                            .where { SignalTable.name inList keyNames }
                            .andWhere { SignalTable.id.inSubQuery(includedSignalIds) }
                            .limit(1)
                            .map {
                                SignalModel(
                                    id = it[SignalTable.id].value,
                                    name = it[SignalTable.name],
                                    type = it[SignalTable.type],
                                    protocol = it[SignalTable.protocol],
                                    address = it[SignalTable.address],
                                    command = it[SignalTable.command],
                                    frequency = it[SignalTable.frequency],
                                    dutyCycle = it[SignalTable.dutyCycle],
                                    data = it[SignalTable.data],
                                )
                            }
                            .first()
                        val response = SignalResponseModel(
                            signalResponse = SignalResponse(
                                signalModel = signalModel,
                                message = order.message,
                                categoryName = category.meta.manifest.singularDisplayName,
                                data = order.data
                            )
                        )
                        context.respond(response)
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

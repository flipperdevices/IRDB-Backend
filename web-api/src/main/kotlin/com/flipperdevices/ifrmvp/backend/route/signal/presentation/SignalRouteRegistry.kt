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
import org.jetbrains.exposed.sql.transactions.transaction

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
                println("REQUEST: $signalRequestModel")
                // Index of successful results
                val index = signalRequestModel.successResults.size

                val brand = tableDao.getBrandById(signalRequestModel.brandId)
                val category = tableDao.getCategoryById(brand.categoryId)
                val categoryType = CategoryType
                    .entries
                    .firstOrNull { it.folderName == category.folderName }
                    ?: error("Could not find category with folderName ${category.folderName}")
                val order = transaction(database) {
                    AllCategoryConfigGenerator
                        .generate(categoryType)
                        .orders
                        .getOrNull(index)
                }

                // Keep only those files, in which signals have been successfully passed
                val includedFileIds = transaction(database) {
                    InfraredFileToSignalTable
                        .select(InfraredFileToSignalTable.infraredFileId)
                        .groupBy(InfraredFileToSignalTable.infraredFileId)
                        .apply {
                            val successSignalIds = signalRequestModel
                                .successResults
                                .map(SignalRequestModel.SignalResultData::signalId)
                            if (successSignalIds.isNotEmpty()) {
                                where {
                                    InfraredFileToSignalTable
                                        .signalId
                                        .inList(successSignalIds)
                                }
                            } else {
                                this
                            }
                        }
                }
                // [MAKEEVRSERG] signal id: 74480; name:power
                // [MAKEEVRSERG] signal id: 75100; name:power
                // [MAKEEVRSERG] signal id: 75148; name:power
                // [MAKEEVRSERG] signal id: 75707; name:power
                val includedInfraredFilesCount = transaction(database) { includedFileIds.count() }
                when (includedInfraredFilesCount) {
                    0L -> {
                        TODO()
                    }

                    1L -> {
                        val infraredFileId = transaction(database) {
                            includedFileIds
                                .map { it[InfraredFileToSignalTable.infraredFileId] }
                                .first()
                                .value
                        }
                        val response = SignalResponseModel(ifrFileModel = tableDao.ifrFileById(infraredFileId))
                        context.respond(response)
                    }

                    else -> {
                        val includedSignalIds = transaction(database) {
                            InfraredFileToSignalTable
                                .select(InfraredFileToSignalTable.signalId)
                                .groupBy(InfraredFileToSignalTable.signalId)
                                .where {
                                    InfraredFileToSignalTable
                                        .infraredFileId
                                        .inSubQuery(includedFileIds)
                                }
                        }
                        if (order == null) {
                            val infraredFileId = transaction(database) {
                                includedFileIds
                                    .map { it[InfraredFileToSignalTable.infraredFileId] }
                                    .first()
                                    .value
                            }
                            val response = SignalResponseModel(ifrFileModel = tableDao.ifrFileById(infraredFileId))
                            context.respond(response)
                            return@post
                        }
                        val keyNames = AnyKeyNamesProvider.getKeyNames(order.key)
                        val signalModel = transaction(database) {
                            SignalTable.selectAll()
                                .where { SignalTable.name inList keyNames }
                                .andWhere { SignalTable.id.inSubQuery(includedSignalIds) }
                                .andWhere { SignalTable.brandId eq brand.id }
                                .andWhere {
                                    val failedSignalIds = signalRequestModel.failedResults
                                        .map(SignalRequestModel.SignalResultData::signalId)
                                    SignalTable.id.notInList(failedSignalIds)
                                }
                                .andWhere {
                                    val successfulSignalIds = signalRequestModel.successResults
                                        .map(SignalRequestModel.SignalResultData::signalId)
                                    SignalTable.id.notInList(successfulSignalIds)
                                }
                                .limit(1)
                                .map {
                                    println(
                                        "[MAKEEVRSERG] signal id: ${it[SignalTable.id].value}; name:${it[SignalTable.name]}"
                                    )
                                    SignalModel(
                                        id = it[SignalTable.id].value,
                                        remote = SignalModel.FlipperRemote(
                                            name = it[SignalTable.name],
                                            type = it[SignalTable.type],
                                            protocol = it[SignalTable.protocol],
                                            address = it[SignalTable.address],
                                            command = it[SignalTable.command],
                                            frequency = it[SignalTable.frequency],
                                            dutyCycle = it[SignalTable.dutyCycle],
                                            data = it[SignalTable.data],
                                        )
                                    )
                                }
                                .first()
                        }
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

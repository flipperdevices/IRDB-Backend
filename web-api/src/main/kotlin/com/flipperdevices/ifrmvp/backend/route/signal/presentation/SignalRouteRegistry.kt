package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.table.InfraredFileToSignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.model.BrandModel
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import com.flipperdevices.ifrmvp.generator.config.category.api.AllCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryType
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyKeyNamesProvider
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.wrapAsExpression

@Suppress("UnusedPrivateProperty")
internal class SignalRouteRegistry(
    private val database: Database,
    private val tableDao: TableDao,
) : RouteRegistry {

    // Keep only those files, in which signals have been successfully passed
    private fun getIncludedFileIds(signalRequestModel: SignalRequestModel): Query {
        return transaction(database) {
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
    }

    private suspend fun getSignalModel(
        signalRequestModel: SignalRequestModel,
        includedFileIds: Query,
        order: CategoryConfiguration.OrderModel,
        brand: BrandModel
    ): SignalModel? {
        val includedSignalIdsQuery = transaction(database) {
            InfraredFileToSignalTable
                .select(InfraredFileToSignalTable.signalId)
                .groupBy(InfraredFileToSignalTable.signalId)
                .where {
                    InfraredFileToSignalTable
                        .infraredFileId
                        .inSubQuery(includedFileIds)
                }
        }
        val keyNames = AnyKeyNamesProvider.getKeyNames(order.key)
        return transaction(database) {
            SignalTable.selectAll()
                .where { SignalTable.name inList keyNames }
                .andWhere { SignalTable.id.inSubQuery(includedSignalIdsQuery) }
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
                .orderBy(
                    wrapAsExpression<Long>(
                        InfraredFileToSignalTable
                            .select(InfraredFileToSignalTable.signalId.count())
                            .where { InfraredFileToSignalTable.signalId eq SignalTable.id }
                    ) to SortOrder.DESC
                )
                .limit(1)
                .map {
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
                    ).also { println("[MAKEEVRSERG]: $it") }
                }
                .firstOrNull()
        }
    }

    private suspend fun findSignal(
        signalRequestModel: SignalRequestModel,
        includedFileIds: Query,
        brand: BrandModel,
        // Index of successful results
        index: Int = signalRequestModel.successResults.size,
        categoryType: CategoryType,
        category: DeviceCategory
    ): SignalResponseModel {
        val order = transaction(database) {
            AllCategoryConfigGenerator
                .generate(categoryType)
                .orders
                .getOrNull(index)
        }

        if (order == null) {
            val infraredFileId = transaction(database) {
                includedFileIds
                    .map { it[InfraredFileToSignalTable.infraredFileId] }
                    .first()
                    .value
            }
            val response = SignalResponseModel(ifrFileModel = tableDao.ifrFileById(infraredFileId))
            return response
        }

        val signalModel = getSignalModel(
            signalRequestModel = signalRequestModel,
            includedFileIds = includedFileIds,
            order = order,
            brand = brand
        )
        if (signalModel == null) {
            return findSignal(
                signalRequestModel = signalRequestModel,
                includedFileIds = includedFileIds,
                brand = brand,
                index = index + 1,
                categoryType = categoryType,
                category = category
            )
        }

        val response = SignalResponseModel(
            signalResponse = SignalResponse(
                signalModel = signalModel,
                message = order.message,
                categoryName = category.meta.manifest.singularDisplayName,
                data = order.data
            )
        )
        return response
    }

    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                @Suppress("UnusedPrivateProperty")
                val signalRequestModel = context.receive<SignalRequestModel>()
                println("REQUEST: $signalRequestModel")

                val brand = tableDao.getBrandById(signalRequestModel.brandId)
                val category = tableDao.getCategoryById(brand.categoryId)
                val categoryType = CategoryType
                    .entries
                    .firstOrNull { it.folderName == category.folderName }
                    ?: error("Could not find category with folderName ${category.folderName}")

                val includedFileIds = getIncludedFileIds(signalRequestModel)
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
                        val response = findSignal(
                            signalRequestModel = signalRequestModel,
                            includedFileIds = includedFileIds,
                            brand = brand,
                            categoryType = categoryType,
                            category = category
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

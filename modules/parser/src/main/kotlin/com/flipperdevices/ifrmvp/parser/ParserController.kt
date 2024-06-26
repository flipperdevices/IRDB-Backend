package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.core.IoCoroutineScope
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.ifrmvp.model.buttondata.SingleKeyButtonData
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.model.OrderModel
import com.flipperdevices.ifrmvp.parser.model.RawIfrRemote
import com.flipperdevices.infrared.editor.model.InfraredRemote
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class ParserController(
    private val signalTableApi: SignalTableApi
) : CoroutineScope by IoCoroutineScope() {

    private suspend fun addOrder(
        orderModels: List<OrderModel>,
        remote: RawIfrRemote,
        ifrSignalId: Long,
        categoryId: Long,
        brandId: Long,
        ifrFileId: Long
    ) {
        orderModels
            .filter { orderModel ->
                val keyIdentifier = when (orderModel.data) {
                    is SingleKeyButtonData -> orderModel.data.keyIdentifier
                    else -> error("Type ${orderModel.data::class} is not supported")
                }
                when (keyIdentifier) {
                    is IfrKeyIdentifier.Name -> {
                        remote.name == keyIdentifier.name
                    }

                    is IfrKeyIdentifier.Sha256 -> error("Comparison by SHA not supported")
                }
            }
            .forEach { orderModel ->
                signalTableApi.addOrderModel(
                    orderModel = orderModel,
                    ifrSignalId = ifrSignalId,
                    categoryId = categoryId,
                    brandId = brandId,
                    ifrFileId = ifrFileId
                )
            }
    }

    private suspend fun fillIrSignal(
        ifrFileId: Long,
        categoryId: Long,
        brandId: Long,
        remotes: List<InfraredRemote>,
        categoryName: String,
        brandName: String,
        ifrFolderName: String
    ) = coroutineScope {
        val orders = ParserPathResolver.controllerOrders(
            category = categoryName,
            brand = brandName,
            controller = ifrFolderName
        )
        remotes.map { remote ->
            val parsed = remote as? InfraredRemote.Parsed
            val raw = remote as? InfraredRemote.Raw
            async {
                val rawRemote = RawIfrRemote(
                    name = remote.name,
                    type = remote.type,
                    protocol = parsed?.protocol,
                    address = parsed?.address,
                    command = parsed?.command,
                    frequency = raw?.frequency,
                    dutyCycle = raw?.dutyCycle,
                    data = raw?.data
                )
                val ifrSignalId = signalTableApi.addSignal(
                    categoryId = categoryId,
                    brandId = brandId,
                    irFileId = ifrFileId,
                    remote = rawRemote,
                )
                addOrder(
                    orderModels = orders,
                    remote = rawRemote,
                    ifrSignalId = ifrSignalId,
                    categoryId = categoryId,
                    brandId = brandId,
                    ifrFileId = ifrFileId
                )
            }
        }.awaitAll()
    }

    private suspend fun fillIrFiles(
        categoryId: Long,
        brandId: Long,
        irFiles: List<File>,
        categoryName: String,
        brandName: String
    ) = coroutineScope {
        irFiles.map { irFile ->
            async {
                val irFileId = signalTableApi.addIrFile(
                    fileName = irFile.name,
                    categoryId = categoryId,
                    brandId = brandId
                )
                val signals = irFile.readText()
                    .let(FlipperFileFormat::fromFileContent)
                    .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
                fillIrSignal(
                    ifrFileId = irFileId,
                    categoryId = categoryId,
                    brandId = brandId,
                    remotes = signals,
                    categoryName = categoryName,
                    brandName = brandName,
                    ifrFolderName = irFile.parentFile.name
                )
            }
        }.awaitAll()
    }

    private suspend fun fillBrands(
        brands: List<File>,
        categoryId: Long,
        categoryName: String
    ) = coroutineScope {
        brands.map { brandFile ->
            async {
                val brandName = brandFile.name
                val brandId = signalTableApi.addBrand(
                    categoryId = categoryId,
                    displayName = brandName
                )
                fillIrFiles(
                    categoryId = categoryId,
                    brandId = brandId,
                    categoryName = categoryName,
                    brandName = brandName,
                    irFiles = ParserPathResolver.brandIfrFiles(
                        category = categoryName,
                        brand = brandName
                    )
                )
            }
        }.awaitAll()
    }

    private suspend fun fillCategoryMeta(
        categoryId: Long,
        categoryName: String
    ) {
        signalTableApi.addCategoryMeta(
            categoryId = categoryId,
            meta = ParserPathResolver.categoryMeta(category = categoryName)
        )
    }

    private suspend fun fillCategories() = coroutineScope {
        ParserPathResolver.categories.map { categoryFile ->
            val categoryName = categoryFile.name
            val categoryId = signalTableApi.addCategory(
                categoryFolderName = categoryName,
            )
            fillCategoryMeta(
                categoryId = categoryId,
                categoryName = categoryName
            )
            fillBrands(
                brands = ParserPathResolver.brands(categoryName),
                categoryId = categoryId,
                categoryName = categoryName
            )
        }
    }

    fun start() = launch { fillCategories() }
}

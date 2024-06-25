package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.core.IoCoroutineScope
import com.flipperdevices.ifrmvp.backend.db.signal.api.SignalTableApi
import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType
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

    private suspend fun fillIrSignal(
        ifrFileId: Long,
        categoryId: Long,
        brandId: Long,
        remotes: List<InfraredRemote>
    ) = coroutineScope {
        remotes.map { remote ->
            val parsed = remote as? InfraredRemote.Parsed
            val raw = remote as? InfraredRemote.Raw
            async {
                signalTableApi.addSignal(
                    categoryId = categoryId,
                    brandId = brandId,
                    irFileId = ifrFileId,
                    name = remote.name,
                    type = remote.type,
                    protocol = parsed?.protocol,
                    address = parsed?.address,
                    command = parsed?.command,
                    frequency = raw?.frequency,
                    dutyCycle = raw?.dutyCycle,
                    data = raw?.data
                )
            }
        }.awaitAll()
    }

    private suspend fun fillIrFiles(
        categoryId: Long,
        brandId: Long,
        irFiles: List<File>
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
                    remotes = signals
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
            async {
                val categoryName = categoryFile.name
                val categoryId = signalTableApi.addCategory(
                    displayName = categoryName,
                    deviceType = DeviceCategoryType.TV
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
        }.awaitAll()
    }

    fun start() = launch { fillCategories() }
}

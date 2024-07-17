package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File

internal class BrandsFiller(
    private val signalTableApi: SignalTableApi,
    private val irFilesFiller: IrFilesFiller
) {

    suspend fun full(model: Model) = coroutineScope {
        with(model) {
            brands.map { brandFile ->
                async {
                    val brandName = brandFile.name
                    val brandId = signalTableApi.addBrand(
                        categoryId = categoryId,
                        displayName = brandName
                    )
                    irFilesFiller.fill(
                        model = IrFilesFiller.Model(
                            categoryId = categoryId,
                            brandId = brandId,
                            categoryName = categoryName,
                            brandName = brandName,
                            irFiles = ParserPathResolver.brandIfrFiles(
                                category = categoryName,
                                brand = brandName
                            )
                        )
                    )
                }
            }.awaitAll()
        }
    }
    data class Model(
        val brands: List<File>,
        val categoryId: Long,
        val categoryName: String
    )
}

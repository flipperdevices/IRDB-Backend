package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlinx.coroutines.coroutineScope

internal class CategoriesFiller(
    private val signalTableApi: SignalTableApi,
    private val metaFiller: MetaFiller,
    private val brandsFiller: BrandsFiller
) {
    suspend fun fill() = coroutineScope {
        ParserPathResolver.categories.map { categoryFile ->
            val categoryName = categoryFile.name
            val categoryId = signalTableApi.addCategory(
                categoryFolderName = categoryName,
            )
            metaFiller.fill(
                model = MetaFiller.Model(
                    categoryId = categoryId,
                    categoryName = categoryName
                )
            )
            brandsFiller.full(
                model = BrandsFiller.Model(
                    brands = ParserPathResolver.brands(categoryName),
                    categoryId = categoryId,
                    categoryName = categoryName
                )
            )
        }
    }
}

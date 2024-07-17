package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver

internal class MetaFiller(private val signalTableApi: SignalTableApi) {

    suspend fun fill(model: Model) = with(model) {
        signalTableApi.addCategoryMeta(
            categoryId = categoryId,
            meta = ParserPathResolver.categoryMeta(
                category = categoryName
            )
        )
    }

    data class Model(
        val categoryId: Long,
        val categoryName: String
    )
}

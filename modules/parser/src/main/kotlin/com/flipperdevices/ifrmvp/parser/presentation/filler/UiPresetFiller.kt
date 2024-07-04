package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import kotlinx.coroutines.coroutineScope

internal class UiPresetFiller(
    private val signalTableApi: SignalTableApi,
) {
    suspend fun fill(model: Model) = coroutineScope {
        with(model) {
            signalTableApi.addUiPreset(
                categoryId = categoryId,
                brandId = brandId,
                irFileId = ifrFileId,
                fileName = uiFileName
            )
        }
    }

    data class Model(
        val categoryId: Long,
        val brandId: Long,
        val ifrFileId: Long,
        val uiFileName: String
    )
}

package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File

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

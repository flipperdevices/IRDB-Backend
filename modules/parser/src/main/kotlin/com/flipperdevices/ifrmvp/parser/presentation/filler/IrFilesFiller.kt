package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File

internal class IrFilesFiller(
    private val signalTableApi: SignalTableApi,
    private val ifrSignalFiller: IfrSignalFiller
) {
    suspend fun fill(model: Model) = coroutineScope {
        with(model) {
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
                    ifrSignalFiller.fill(
                        model = IfrSignalFiller.Model(
                            ifrFileId = irFileId,
                            categoryId = categoryId,
                            brandId = brandId,
                            remotes = signals,
                            categoryName = categoryName,
                            brandName = brandName,
                            ifrFolderName = irFile.parentFile.name
                        )
                    )
                }
            }.awaitAll()
        }
    }

    data class Model(
        val categoryId: Long,
        val brandId: Long,
        val irFiles: List<File>,
        val categoryName: String,
        val brandName: String
    )
}

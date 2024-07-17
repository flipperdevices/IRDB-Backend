package com.flipperdevices.ifrmvp.parser.presentation.filler

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File

internal class InfraredFilesFiller(
    private val signalTableApi: SignalTableApi,
    private val infraredSignalsFiller: InfraredSignalsFiller,
    private val uiFileFiller: UiPresetFiller
) {
    suspend fun fill(model: Model) = coroutineScope {
        with(model) {
            irFiles.map { irFile ->

                async {
                    val irFileId = signalTableApi.addIrFile(
                        fileName = irFile.name,
                        categoryId = categoryId,
                        brandId = brandId,
                        folderName = irFile.parentFile.name
                    )
                    irFile.parentFile.listFiles()
                        .orEmpty()
                        .filter { file -> file.name.endsWith(".ui.json") }
                        .onEach { file ->
                            uiFileFiller.fill(
                                UiPresetFiller.Model(
                                    categoryId = categoryId,
                                    brandId = brandId,
                                    ifrFileId = irFileId,
                                    uiFileName = file.name
                                )
                            )
                        }
                    val signals = irFile.readText()
                        .let(FlipperFileFormat::fromFileContent)
                        .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
                    infraredSignalsFiller.fill(
                        model = InfraredSignalsFiller.Model(
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

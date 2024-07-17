package com.flipperdevices.ifrmvp.parser.presentation

import com.flipperdevices.ifrmvp.backend.core.IoCoroutineScope
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.presentation.filler.BrandsFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.CategoriesFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.InfraredFilesFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.InfraredSignalsFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.MetaFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.UiPresetFiller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class FillerController(
    signalTableApi: SignalTableApi
) : CoroutineScope by IoCoroutineScope() {
    private val infraredSignalsFiller = InfraredSignalsFiller(signalTableApi)
    private val uiFileFiller = UiPresetFiller(signalTableApi)
    private val infraredFilesFiller = InfraredFilesFiller(signalTableApi, infraredSignalsFiller, uiFileFiller)
    private val brandsFiller = BrandsFiller(signalTableApi, infraredFilesFiller)
    private val metaFilter = MetaFiller(signalTableApi)
    private val categoriesFiller = CategoriesFiller(signalTableApi, metaFilter, brandsFiller)

    fun fillDatabase() = launch { categoriesFiller.fill() }
}

package com.flipperdevices.ifrmvp.parser.presentation

import com.flipperdevices.ifrmvp.backend.core.IoCoroutineScope
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.presentation.filler.BrandsFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.CategoriesFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.IfrSignalFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.IrFilesFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.MetaFiller
import com.flipperdevices.ifrmvp.parser.presentation.filler.OrderFiller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class FillerController(
    signalTableApi: SignalTableApi
) : CoroutineScope by IoCoroutineScope() {
    private val orderFiller = OrderFiller(signalTableApi)
    private val ifrSignalFiller = IfrSignalFiller(signalTableApi, orderFiller)
    private val irFilesFiller = IrFilesFiller(signalTableApi, ifrSignalFiller)
    private val brandsFiller = BrandsFiller(signalTableApi, irFilesFiller)
    private val metaFilter = MetaFiller(signalTableApi)
    private val categoriesFiller = CategoriesFiller(signalTableApi, metaFilter, brandsFiller)

    fun fillDatabase() = launch { categoriesFiller.fill() }
}

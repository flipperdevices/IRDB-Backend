package com.flipperdevices.ifrmvp.parser.di

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.api.SignalTableApiImpl
import com.flipperdevices.ifrmvp.parser.presentation.FillerController

internal interface ParserModule {
    val signalTableApi: SignalTableApi
    val fillerController: FillerController

    class Default(signalApiModule: SignalApiModule) : ParserModule {
        override val signalTableApi: SignalTableApi by lazy {
            SignalTableApiImpl(signalApiModule.database)
        }
        override val fillerController: FillerController by lazy {
            FillerController(signalTableApi)
        }
    }
}

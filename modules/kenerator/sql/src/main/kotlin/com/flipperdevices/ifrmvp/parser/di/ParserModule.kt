package com.flipperdevices.ifrmvp.parser.di

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.parser.presentation.FillerController

internal interface ParserModule {
    val fillerController: FillerController

    class Default(signalApiModule: SignalApiModule) : ParserModule {
        override val fillerController: FillerController by lazy {
            FillerController(signalApiModule.database)
        }
    }
}

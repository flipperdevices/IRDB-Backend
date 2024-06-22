package com.flipperdevices.ifrmvp.parser.di

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.parser.ParserController

interface ParserModule {
    val parserController: ParserController

    class Default(signalApiModule: SignalApiModule) : ParserModule {
        override val parserController: ParserController by lazy {
            ParserController(signalApiModule.signalTableApi)
        }
    }
}

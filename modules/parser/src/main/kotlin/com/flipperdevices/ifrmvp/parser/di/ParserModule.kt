package com.flipperdevices.ifrmvp.parser.di

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.parser.ParserController
import com.flipperdevices.ifrmvp.parser.api.SignalTableApi
import com.flipperdevices.ifrmvp.parser.api.SignalTableApiImpl

interface ParserModule {
    val signalTableApi: SignalTableApi
    val parserController: ParserController

    class Default(signalApiModule: SignalApiModule) : ParserModule {
        override val signalTableApi: SignalTableApi by lazy {
            SignalTableApiImpl(signalApiModule.database)
        }
        override val parserController: ParserController by lazy {
            ParserController(signalTableApi)
        }
    }
}

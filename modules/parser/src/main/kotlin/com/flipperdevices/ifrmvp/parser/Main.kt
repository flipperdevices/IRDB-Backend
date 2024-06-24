package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import com.flipperdevices.ifrmvp.parser.di.ParserModule
import kotlinx.coroutines.runBlocking

fun main() {
    val parserModule = ParserModule.Default(
        signalApiModule = SignalApiModule.Default(
            signalDbConnection = DBConnection.H2("signal_database")
        )
    )
    runBlocking {
        parserModule.parserController.start().join()
    }
}
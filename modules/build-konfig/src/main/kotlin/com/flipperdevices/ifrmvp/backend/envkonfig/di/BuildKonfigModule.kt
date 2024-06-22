package com.flipperdevices.ifrmvp.backend.envkonfig.di

import com.flipperdevices.ifrmvp.backend.envkonfig.Connection
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import com.flipperdevices.ifrmvp.backend.envkonfig.di.factory.ConnectionFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.di.factory.SignalDBConnectionFactory

interface BuildKonfigModule {
    val connection: Connection
    val signalDbConnection: DBConnection

    object Default : BuildKonfigModule {
        override val connection: Connection = ConnectionFactory.create()
        override val signalDbConnection: DBConnection = SignalDBConnectionFactory.create()
    }
}

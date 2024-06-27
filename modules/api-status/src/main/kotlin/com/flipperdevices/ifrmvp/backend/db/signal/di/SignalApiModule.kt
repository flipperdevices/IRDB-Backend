package com.flipperdevices.ifrmvp.backend.db.signal.di

import com.flipperdevices.ifrmvp.backend.db.signal.di.factory.SignalDatabaseFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.model.DBConnection
import org.jetbrains.exposed.sql.Database

interface SignalApiModule {
    val database: Database

    class Default(
        signalDbConnection: DBConnection
    ) : SignalApiModule {
        override val database: Database by lazy {
            SignalDatabaseFactory(signalDbConnection).create()
        }
    }
}

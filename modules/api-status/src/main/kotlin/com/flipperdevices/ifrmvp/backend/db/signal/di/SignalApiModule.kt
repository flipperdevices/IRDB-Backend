package com.flipperdevices.ifrmvp.backend.db.signal.di

import com.flipperdevices.ifrmvp.backend.db.signal.api.SignalTableApi
import com.flipperdevices.ifrmvp.backend.db.signal.api.SignalTableApiImpl
import com.flipperdevices.ifrmvp.backend.db.signal.di.factory.SignalDatabaseFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import org.jetbrains.exposed.sql.Database

interface SignalApiModule {

    val database: Database
    val signalTableApi: SignalTableApi

    class Default(
        signalDbConnection: DBConnection
    ) : SignalApiModule {
        override val database: Database by lazy {
            SignalDatabaseFactory(signalDbConnection).create()
        }
        override val signalTableApi: SignalTableApi by lazy {
            SignalTableApiImpl(database)
        }
    }
}

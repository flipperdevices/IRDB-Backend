package com.flipperdevices.ifrmvp.backend.db.signal.di

import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDaoImpl
import com.flipperdevices.ifrmvp.backend.db.signal.di.factory.SignalDatabaseFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.model.DBConnection
import org.jetbrains.exposed.sql.Database

interface SignalApiModule {
    val database: Database
    val tableDao: TableDao

    class Default(
        signalDbConnection: DBConnection
    ) : SignalApiModule {
        override val database: Database by lazy {
            SignalDatabaseFactory(signalDbConnection).create()
        }
        override val tableDao: TableDao by lazy {
            TableDaoImpl(database)
        }
    }
}

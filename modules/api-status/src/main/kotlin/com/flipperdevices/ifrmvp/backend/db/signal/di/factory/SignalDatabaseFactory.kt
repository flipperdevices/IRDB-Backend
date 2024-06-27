package com.flipperdevices.ifrmvp.backend.db.signal.di.factory

import com.flipperdevices.ifrmvp.backend.core.di.factory.DatabaseFactory
import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalOrderTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.SignalTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.UiPresetTable
import com.flipperdevices.ifrmvp.backend.envkonfig.model.DBConnection
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

internal class SignalDatabaseFactory(
    private val dbConnection: DBConnection
) : DatabaseFactory by DatabaseFactory.Default(
    dbConnection = dbConnection,
    configure = { database ->
        TransactionManager.manager.defaultIsolationLevel = java.sql.Connection.TRANSACTION_SERIALIZABLE
        runBlocking {
            transaction(database) {
                addLogger(Slf4jSqlDebugLogger)
                SchemaUtils.create(
                    BrandTable,
                    CategoryMetaTable,
                    CategoryTable,
                    IfrFileTable,
                    SignalOrderTable,
                    SignalTable,
                    UiPresetTable,
                )
            }
        }
    }
)

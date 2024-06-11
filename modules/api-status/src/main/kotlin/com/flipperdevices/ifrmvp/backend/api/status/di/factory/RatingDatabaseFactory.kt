package com.flipperdevices.ifrmvp.backend.api.status.di.factory

import com.flipperdevices.ifrmvp.backend.api.status.db.StatusTable
import com.flipperdevices.ifrmvp.backend.core.di.factory.DatabaseFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

internal class RatingDatabaseFactory(
    private val dbConnection: DBConnection
) : DatabaseFactory by DatabaseFactory.Default(
    dbConnection = dbConnection,
    configure = { database ->
        TransactionManager.manager.defaultIsolationLevel = java.sql.Connection.TRANSACTION_SERIALIZABLE
        runBlocking {
            transaction(database) {
                addLogger(Slf4jSqlDebugLogger)
                SchemaUtils.create(
                    StatusTable,
                )
            }
        }
    }
)

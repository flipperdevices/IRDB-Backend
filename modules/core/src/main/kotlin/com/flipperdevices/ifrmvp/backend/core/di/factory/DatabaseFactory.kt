package com.flipperdevices.ifrmvp.backend.core.di.factory

import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import org.jetbrains.exposed.sql.Database

interface DatabaseFactory {
    fun create(): Database

    class Default(
        private val dbConnection: DBConnection,
        private val configure: (Database) -> Unit
    ) : DatabaseFactory {
        override fun create(): Database {
            return when (dbConnection) {
                is DBConnection.MySql -> {
                    Database.connect(
                        url = dbConnection.url,
                        driver = dbConnection.driver,
                        user = dbConnection.user,
                        password = dbConnection.password
                    )
                }

                is DBConnection.SQLite -> {
                    Database.connect(
                        url = dbConnection.url,
                        driver = dbConnection.driver
                    )
                }

                is DBConnection.H2 -> {
                    Database.connect(
                        url = "jdbc:h2:./${dbConnection.name};DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
                        driver = dbConnection.driver
                    )
                }
            }.also(configure)
        }
    }
}

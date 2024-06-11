package com.flipperdevices.ifrmvp.backend.api.status.di

import com.flipperdevices.ifrmvp.backend.api.status.StatusApi
import com.flipperdevices.ifrmvp.backend.api.status.StatusApiImpl
import com.flipperdevices.ifrmvp.backend.api.status.di.factory.RatingDatabaseFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import org.jetbrains.exposed.sql.Database

interface StatusApiModule {

    val statusApi: StatusApi

    class Default(
        ratingDbConnection: DBConnection
    ) : StatusApiModule {
        private val database: Database by lazy {
            RatingDatabaseFactory(ratingDbConnection).create()
        }
        override val statusApi: StatusApi by lazy {
            StatusApiImpl(database)
        }
    }
}

package com.flipperdevices.ifrmvp.backend.envkonfig.di

import com.flipperdevices.ifrmvp.backend.envkonfig.Connection
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import com.flipperdevices.ifrmvp.backend.envkonfig.di.factory.ConnectionFactory
import com.flipperdevices.ifrmvp.backend.envkonfig.di.factory.RatingDBConnectionFactory

interface BuildKonfigModule {
    val connection: Connection
    val ratingDatabaseConnection: DBConnection

    object Default : BuildKonfigModule {
        override val connection: Connection = ConnectionFactory.create()
        override val ratingDatabaseConnection: DBConnection = RatingDBConnectionFactory.create()
    }
}

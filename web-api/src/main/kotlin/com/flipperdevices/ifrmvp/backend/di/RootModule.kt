package com.flipperdevices.ifrmvp.backend.di

import com.flipperdevices.ifrmvp.backend.api.status.di.StatusApiModule
import com.flipperdevices.ifrmvp.backend.core.di.CoreModule
import com.flipperdevices.ifrmvp.backend.envkonfig.di.BuildKonfigModule
import com.flipperdevices.ifrmvp.backend.route.status.di.StatusModule

interface RootModule {

    val coreModule: CoreModule
    val buildKonfigModule: BuildKonfigModule

    val statusApiModule: StatusApiModule

    val statusModule: StatusModule

    class Default : RootModule {
        override val coreModule by lazy {
            CoreModule.Default()
        }
        override val buildKonfigModule: BuildKonfigModule by lazy {
            BuildKonfigModule.Default()
        }

        override val statusApiModule: StatusApiModule by lazy {
            StatusApiModule.Default(
                ratingDbConnection = buildKonfigModule.ratingDatabaseConnection
            )
        }

        override val statusModule: StatusModule by lazy {
            StatusModule.Default()
        }
    }
}

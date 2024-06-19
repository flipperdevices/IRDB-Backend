package com.flipperdevices.ifrmvp.backend.di

import com.flipperdevices.ifrmvp.backend.api.status.di.StatusApiModule
import com.flipperdevices.ifrmvp.backend.core.di.CoreModule
import com.flipperdevices.ifrmvp.backend.envkonfig.di.BuildKonfigModule
import com.flipperdevices.ifrmvp.backend.route.brands.di.BrandsModule
import com.flipperdevices.ifrmvp.backend.route.categories.di.CategoriesModule
import com.flipperdevices.ifrmvp.backend.route.signal.di.SignalModule

interface RootModule {

    val coreModule: CoreModule
    val buildKonfigModule: BuildKonfigModule

    val statusApiModule: StatusApiModule

    val categoriesModule: CategoriesModule
    val brandsModule: BrandsModule
    val signalModule: SignalModule

    class Default : RootModule {
        override val coreModule by lazy {
            CoreModule.Default()
        }
        override val buildKonfigModule: BuildKonfigModule by lazy {
            BuildKonfigModule.Default
        }

        override val statusApiModule: StatusApiModule by lazy {
            StatusApiModule.Default(
                ratingDbConnection = buildKonfigModule.ratingDatabaseConnection
            )
        }

        override val categoriesModule: CategoriesModule by lazy {
            CategoriesModule.Default()
        }
        override val brandsModule: BrandsModule by lazy {
            BrandsModule.Default()
        }
        override val signalModule: SignalModule by lazy {
            SignalModule.Default()
        }
    }
}

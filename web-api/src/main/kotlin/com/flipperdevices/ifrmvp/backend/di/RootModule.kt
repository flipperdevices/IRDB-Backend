package com.flipperdevices.ifrmvp.backend.di

import com.flipperdevices.ifrmvp.backend.core.di.CoreModule
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.envkonfig.di.BuildKonfigModule
import com.flipperdevices.ifrmvp.backend.route.brands.di.BrandsModule
import com.flipperdevices.ifrmvp.backend.route.categories.di.CategoriesModule
import com.flipperdevices.ifrmvp.backend.route.signal.di.SignalModule
import com.flipperdevices.ifrmvp.parser.di.ParserModule

interface RootModule {

    val coreModule: CoreModule
    val buildKonfigModule: BuildKonfigModule

    val signalApiModule: SignalApiModule

    val parserModule: ParserModule

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

        override val signalApiModule: SignalApiModule by lazy {
            SignalApiModule.Default(
                signalDbConnection = buildKonfigModule.signalDbConnection
            )
        }

        override val parserModule: ParserModule by lazy {
            ParserModule.Default(signalApiModule)
        }

        override val categoriesModule: CategoriesModule by lazy {
            CategoriesModule.Default(signalApiModule)
        }
        override val brandsModule: BrandsModule by lazy {
            BrandsModule.Default(signalApiModule)
        }
        override val signalModule: SignalModule by lazy {
            SignalModule.Default(signalApiModule)
        }
    }
}

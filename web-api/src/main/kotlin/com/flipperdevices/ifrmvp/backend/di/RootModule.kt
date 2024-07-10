package com.flipperdevices.ifrmvp.backend.di

import com.flipperdevices.ifrmvp.backend.core.di.CoreModule
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import com.flipperdevices.ifrmvp.backend.route.brands.di.BrandsModule
import com.flipperdevices.ifrmvp.backend.route.categories.di.CategoriesModule
import com.flipperdevices.ifrmvp.backend.route.key.di.KeyModule
import com.flipperdevices.ifrmvp.backend.route.signal.di.SignalModule
import com.flipperdevices.ifrmvp.backend.route.ui.di.UiModule

interface RootModule {

    val coreModule: CoreModule

    val signalApiModule: SignalApiModule

    val categoriesModule: CategoriesModule
    val brandsModule: BrandsModule
    val signalModule: SignalModule
    val keyModule: KeyModule
    val uiModule: UiModule

    class Default : RootModule {
        override val coreModule by lazy {
            CoreModule.Default()
        }

        override val signalApiModule: SignalApiModule by lazy {
            SignalApiModule.Default(
                signalDbConnection = EnvKonfig.signalDatabaseConnection
            )
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
        override val keyModule: KeyModule by lazy {
            KeyModule.Default(signalApiModule)
        }
        override val uiModule: UiModule by lazy {
            UiModule.Default(
                signalApiModule = signalApiModule,
                keyModule = keyModule
            )
        }
    }
}

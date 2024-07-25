package com.flipperdevices.ifrmvp.backend.route.ui.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.key.di.KeyModule
import com.flipperdevices.ifrmvp.backend.route.ui.data.UiFileRepositoryImpl
import com.flipperdevices.ifrmvp.backend.route.ui.presentation.UiRouteRegistry
import com.flipperdevices.ifrmvp.parser.UiGeneratorImpl

interface UiModule {
    val registry: RouteRegistry

    class Default(
        signalApiModule: SignalApiModule,
        keyModule: KeyModule
    ) : UiModule {
        override val registry: RouteRegistry by lazy {
            UiRouteRegistry(
                keyRouteRepository = keyModule.keyRouteRepository,
                uiGenerator = UiGeneratorImpl(),
                uiFileRepository = UiFileRepositoryImpl(
                    tableDao = signalApiModule.tableDao,
                    database = signalApiModule.database
                )
            )
        }
    }
}

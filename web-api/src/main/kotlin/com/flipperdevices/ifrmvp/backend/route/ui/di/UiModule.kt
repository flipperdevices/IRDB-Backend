package com.flipperdevices.ifrmvp.backend.route.ui.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.ui.presentation.UiRouteRegistry

interface UiModule {
    val registry: RouteRegistry

    class Default(signalApiModule: SignalApiModule) : UiModule {
        override val registry: RouteRegistry by lazy {
            UiRouteRegistry(
                signalApiModule.database,
            )
        }
    }
}

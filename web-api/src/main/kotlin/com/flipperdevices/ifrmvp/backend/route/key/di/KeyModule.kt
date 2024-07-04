package com.flipperdevices.ifrmvp.backend.route.key.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.key.presentation.KeyRouteRegistry

interface KeyModule {
    val registry: RouteRegistry

    class Default(signalApiModule: SignalApiModule) : KeyModule {
        override val registry: RouteRegistry by lazy {
            KeyRouteRegistry(
                signalApiModule.database,
            )
        }
    }
}

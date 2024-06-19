package com.flipperdevices.ifrmvp.backend.route.signal.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.signal.presentation.SignalRouteRegistry

interface SignalModule {
    val registry: RouteRegistry

    class Default : SignalModule {
        override val registry: RouteRegistry by lazy {
            SignalRouteRegistry()
        }
    }
}

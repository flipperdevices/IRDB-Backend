package com.flipperdevices.ifrmvp.backend.route.key.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepository
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepositoryImpl
import com.flipperdevices.ifrmvp.backend.route.key.presentation.KeyRouteRegistry

interface KeyModule {
    val registry: RouteRegistry
    val keyRouteRepository: KeyRouteRepository

    class Default(signalApiModule: SignalApiModule) : KeyModule {
        override val keyRouteRepository: KeyRouteRepository = KeyRouteRepositoryImpl(signalApiModule.database)
        override val registry: RouteRegistry by lazy {
            KeyRouteRegistry(
                keyRouteRepository,
            )
        }
    }
}

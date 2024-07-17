package com.flipperdevices.ifrmvp.backend.route.configgen.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.configgen.presentation.ConfigGenRouteRegistry
import com.flipperdevices.ifrmvp.backend.route.key.di.KeyModule

interface ConfigGenModule {
    val registry: RouteRegistry

    class Default(keyModule: KeyModule) : ConfigGenModule {
        override val registry: RouteRegistry by lazy {
            ConfigGenRouteRegistry(
                keyRouteRepository = keyModule.keyRouteRepository
            )
        }
    }
}
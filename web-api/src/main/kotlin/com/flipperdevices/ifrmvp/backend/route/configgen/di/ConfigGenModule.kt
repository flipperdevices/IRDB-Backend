package com.flipperdevices.ifrmvp.backend.route.configgen.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.configgen.presentation.ConfigGenRouteRegistry
import com.flipperdevices.ifrmvp.backend.route.key.di.KeyModule

interface ConfigGenModule {
    val registry: RouteRegistry

    class Default(
        keyModule: KeyModule,
        apiModule: SignalApiModule
    ) : ConfigGenModule {
        override val registry: RouteRegistry by lazy {
            ConfigGenRouteRegistry(
                keyRouteRepository = keyModule.keyRouteRepository,
                tableDao = apiModule.tableDao
            )
        }
    }
}

package com.flipperdevices.ifrmvp.backend.route.signal.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.signal.data.CounterRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.DefaultSignalRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.IrFileRepository
import com.flipperdevices.ifrmvp.backend.route.signal.data.SignalByOrderRepository
import com.flipperdevices.ifrmvp.backend.route.signal.presentation.SignalRouteRegistry

interface SignalModule {
    val registry: RouteRegistry

    class Default(signalApiModule: SignalApiModule) : SignalModule {
        override val registry: RouteRegistry by lazy {
            SignalRouteRegistry(
                signalApiModule.database,
                irFileRepository = IrFileRepository(),
                counterRepository = CounterRepository(),
                defaultSignalRepository = DefaultSignalRepository(),
                signalByOrderRepository = SignalByOrderRepository()
            )
        }
    }
}

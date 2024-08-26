package com.flipperdevices.ifrmvp.backend.route.infrareds.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.brands.data.BrandsRepositoryImpl
import com.flipperdevices.ifrmvp.backend.route.brands.presentation.BrandsRouteRegistry
import com.flipperdevices.ifrmvp.backend.route.infrareds.data.InfraredsRepository
import com.flipperdevices.ifrmvp.backend.route.infrareds.presentation.InfraredsRouteRegistry

interface InfraredsModule {
    val registry: RouteRegistry

    class Default(signalApiModule: SignalApiModule) : InfraredsModule {
        override val registry: RouteRegistry by lazy {
            InfraredsRouteRegistry(
                infraredsRepository = InfraredsRepository(
                    database = signalApiModule.database,
                    tableDao = signalApiModule.tableDao
                )
            )
        }
    }
}

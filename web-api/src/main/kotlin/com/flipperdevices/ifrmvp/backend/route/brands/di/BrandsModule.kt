package com.flipperdevices.ifrmvp.backend.route.brands.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.brands.data.BrandsRepositoryImpl
import com.flipperdevices.ifrmvp.backend.route.brands.presentation.BrandsRouteRegistry

interface BrandsModule {
    val registry: RouteRegistry

    class Default(signalApiModule: SignalApiModule) : BrandsModule {
        override val registry: RouteRegistry by lazy {
            BrandsRouteRegistry(
                brandsRepository = BrandsRepositoryImpl(
                    database = signalApiModule.database,
                    tableDao = signalApiModule.tableDao
                )
            )
        }
    }
}

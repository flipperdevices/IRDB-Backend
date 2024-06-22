package com.flipperdevices.ifrmvp.backend.route.categories.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.route.categories.data.CategoriesRepositoryImpl
import com.flipperdevices.ifrmvp.backend.route.categories.presentation.CategoriesRouteRegistry

interface CategoriesModule {
    val registry: RouteRegistry

    class Default(signalApiModule: SignalApiModule) : CategoriesModule {
        override val registry: RouteRegistry by lazy {
            CategoriesRouteRegistry(
                categoriesRepository = CategoriesRepositoryImpl(
                    database = signalApiModule.database
                )
            )
        }
    }
}

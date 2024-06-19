package com.flipperdevices.ifrmvp.backend.route.brands.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.brands.presentation.BrandsRouteRegistry

interface BrandsModule {
    val registry: RouteRegistry

    class Default : BrandsModule {
        override val registry: RouteRegistry by lazy {
            BrandsRouteRegistry()
        }
    }
}

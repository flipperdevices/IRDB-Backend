package com.flipperdevices.ifrmvp.backend.route.categories.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.categories.presentation.CategoriesRouteRegistry

interface CategoriesModule {
    val registry: RouteRegistry

    class Default : CategoriesModule {
        override val registry: RouteRegistry by lazy {
            CategoriesRouteRegistry()
        }
    }
}

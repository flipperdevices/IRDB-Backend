package com.flipperdevices.ifrmvp.backend.route.categories.di

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.categories.presentation.CategoriesRouteRegistry
import com.flipperdevices.ifrmvp.backend.route.status.di.StatusModule

interface CategoriesModule {
    val registry: RouteRegistry

    class Default : StatusModule {
        override val registry: RouteRegistry by lazy {
            CategoriesRouteRegistry()
        }
    }
}

package com.flipperdevices.ifrmvp.backend.route.categories.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.CategoriesResponse
import com.flipperdevices.ifrmvp.backend.route.categories.data.CategoriesRepository
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class CategoriesRouteRegistry(
    private val categoriesRepository: CategoriesRepository
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "categories",
            builder = { with(CategoriesSwagger) { createSwaggerDefinition() } },
            body = {
                val categories = categoriesRepository.getCategories()
                val categoriesResponse = CategoriesResponse(categories = categories)
                context.respond(categoriesResponse)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

package com.flipperdevices.ifrmvp.backend.route.brands.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.BrandModel
import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class BrandsRouteRegistry : RouteRegistry {

    private fun Routing.statusRoute() {
        post(
            path = "manufacturers",
            builder = { with(BrandsSwagger) { createSwaggerDefinition() } },
            body = {
                val brandsResponse = BrandsResponse(
                    brands = listOf(
                        BrandModel(0, "LG"),
                        BrandModel(1, "Panasonic"),
                    ).sortedBy { brandModel -> brandModel.name.first() }
                )
                context.respond(brandsResponse)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

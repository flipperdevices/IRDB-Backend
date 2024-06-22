package com.flipperdevices.ifrmvp.backend.route.brands.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import com.flipperdevices.ifrmvp.backend.route.brands.data.BrandsRepository
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

internal class BrandsRouteRegistry(
    private val brandsRepository: BrandsRepository
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "manufacturers",
            builder = { with(BrandsSwagger) { createSwaggerDefinition() } },
            body = {
                var i = 0L
                val categoryId = context.request
                    .queryParameters["category_id"]
                    ?.toLongOrNull()
                    ?: 0
                val brandsResponse = BrandsResponse(
                    brands = brandsRepository.getBrands(categoryId)
                        .sortedBy { brandModel -> brandModel.name.first() }
                )
                context.respond(brandsResponse)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

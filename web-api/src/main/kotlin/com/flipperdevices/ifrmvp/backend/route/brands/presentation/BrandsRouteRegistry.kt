package com.flipperdevices.ifrmvp.backend.route.brands.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import com.flipperdevices.ifrmvp.backend.route.brands.data.BrandsRepository
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class BrandsRouteRegistry(
    private val brandsRepository: BrandsRepository
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "brands",
            builder = { with(BrandsSwagger) { createSwaggerDefinition() } },
            body = {
                val categoryId = context.request
                    .queryParameters["category_id"]
                    ?.toLongOrNull()
                    ?: -1
                val query = context.request
                    .queryParameters["query"]
                    .orEmpty()
                val brandsResponse = BrandsResponse(
                    brands = brandsRepository.getBrands(categoryId, query)
                        .sortedBy { brandModel -> brandModel.folderName.lowercase() }
                )
                context.respond(brandsResponse)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

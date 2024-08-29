package com.flipperdevices.ifrmvp.backend.route.infrareds.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import com.flipperdevices.ifrmvp.backend.model.InfraredsResponse
import com.flipperdevices.ifrmvp.backend.route.brands.data.BrandsRepository
import com.flipperdevices.ifrmvp.backend.route.infrareds.data.InfraredsRepository
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class InfraredsRouteRegistry(
    private val infraredsRepository: InfraredsRepository
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "infrareds",
            builder = { with(InfraredsSwagger) { createSwaggerDefinition() } },
            body = {
                val brandId = context.request
                    .queryParameters["brand_id"]
                    ?.toLongOrNull()
                    ?: -1
                val brandsResponse = InfraredsResponse(
                    infraredFiles = infraredsRepository.fetchInfrareds(brandId)
                        .sortedBy { infraredModel -> infraredModel.fileName.lowercase() }
                )
                context.respond(brandsResponse)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

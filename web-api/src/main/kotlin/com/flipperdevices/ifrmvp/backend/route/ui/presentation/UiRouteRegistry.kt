package com.flipperdevices.ifrmvp.backend.route.ui.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database

internal class UiRouteRegistry(
    private val database: Database
) : RouteRegistry {

    private fun Routing.statusRoute() {
        post(
            path = "ui",
            builder = { with(UiSwagger) { createSwaggerDefinition() } },
            body = {

//                context.respond(signalResponseModel)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

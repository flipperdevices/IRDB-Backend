package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.routing.Routing

internal class SignalRouteRegistry : RouteRegistry {

    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

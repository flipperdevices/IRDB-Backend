package com.flipperdevices.ifrmvp.backend.route.status.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

internal class StatusRouteRegistry : RouteRegistry {

    private fun Routing.statusRoute() {
        get("status") {
            context.respond(HttpStatusCode.OK, "Ok")
        }
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

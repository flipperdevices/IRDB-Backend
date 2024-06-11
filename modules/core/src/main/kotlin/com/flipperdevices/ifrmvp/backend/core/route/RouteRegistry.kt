package com.flipperdevices.ifrmvp.backend.core.route

import io.ktor.server.routing.Routing

fun interface RouteRegistry {
    fun register(routing: Routing)
}

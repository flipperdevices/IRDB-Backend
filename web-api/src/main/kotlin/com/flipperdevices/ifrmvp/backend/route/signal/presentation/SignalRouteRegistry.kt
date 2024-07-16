package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database

@Suppress("UnusedPrivateProperty")
internal class SignalRouteRegistry(
    private val database: Database,
) : RouteRegistry {
    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                @Suppress("UnusedPrivateProperty")
                val signalRequestModel = context.receive<SignalRequestModel>()
                TODO()
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }

    companion object {
        private const val MAX_SUCCESS_ROW = 4
    }
}

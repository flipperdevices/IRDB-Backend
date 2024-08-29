package com.flipperdevices.ifrmvp.backend.route.ui.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepository
import com.flipperdevices.ifrmvp.backend.route.ui.data.UiFileRepository
import com.flipperdevices.ifrmvp.kenerator.ui.UiGenerator
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.http.ContentType
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing

internal class UiRouteRegistry(
    private val uiGenerator: UiGenerator,
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "ui",
            builder = { with(UiSwagger) { createSwaggerDefinition() } },
            body = {
                val ifrFileId = context.parameters["ifr_file_id"]?.toLongOrNull() ?: -1

                context.respond(uiGenerator.generate(irFileId = ifrFileId))
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

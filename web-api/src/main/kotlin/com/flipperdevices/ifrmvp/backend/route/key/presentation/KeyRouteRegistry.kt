package com.flipperdevices.ifrmvp.backend.route.key.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.IfrFileContentResponse
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepository
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class KeyRouteRegistry(
    private val keyRouteRepository: KeyRouteRepository
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "key",
            builder = { with(KeySwagger) { createSwaggerDefinition() } },
            body = {
                val ifrFileId = context.parameters["ifr_file_id"]?.toLongOrNull() ?: -1
                val file = keyRouteRepository.getIfrFile(ifrFileId)
                if (!file.exists()) error("Ifr file doesn't exists! ${file.absolutePath}")
                val response = IfrFileContentResponse(file.readText())
                context.respond(response)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

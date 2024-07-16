package com.flipperdevices.ifrmvp.backend.route.configgen.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepository
import com.flipperdevices.ifrmvp.backend.route.key.presentation.KeySwagger
import com.flipperdevices.ifrmvp.generator.config.device.api.DefaultDeviceConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyKeyNamesProvider
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class ConfigGenRouteRegistry(
    private val keyRouteRepository: KeyRouteRepository
) : RouteRegistry {
    private fun Routing.configRoute() {
        get(
            path = "configs",
            builder = { with(KeySwagger) { createSwaggerDefinition() } },
            body = {
                val ifrFileId = context.parameters["ifr_file_id"]?.toLongOrNull() ?: -1
                val file = keyRouteRepository.getIfrFile(ifrFileId)
                if (!file.exists()) error("Ifr file doesn't exists! ${file.absolutePath}")
                context.respond(DefaultDeviceConfigGenerator(AnyKeyNamesProvider).generate(file))
            }
        )
    }

    override fun register(routing: Routing) {
        routing.configRoute()
    }
}

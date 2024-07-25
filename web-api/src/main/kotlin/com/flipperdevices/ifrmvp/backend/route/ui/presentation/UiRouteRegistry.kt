package com.flipperdevices.ifrmvp.backend.route.ui.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepository
import com.flipperdevices.ifrmvp.backend.route.ui.data.UiFileRepository
import com.flipperdevices.ifrmvp.parser.UiGenerator
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.http.ContentType
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing

internal class UiRouteRegistry(
    private val keyRouteRepository: KeyRouteRepository,
    private val uiGenerator: UiGenerator,
    private val uiFileRepository: UiFileRepository
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "ui",
            builder = { with(UiSwagger) { createSwaggerDefinition() } },
            body = {
                val ifrFileId = context.parameters["ifr_file_id"]?.toLongOrNull() ?: -1
                val uiPresetModel = uiFileRepository.getUiFileModelOrNull(ifrFileId)
                if (uiPresetModel == null) {
                    val ifrFile = keyRouteRepository.getIfrFile(ifrFileId)
                    context.respond(uiGenerator.generate(ifrFile.readText()))
                } else {
                    val content = uiFileRepository.getUiFileContent(uiPresetModel)
                    context.respondText(
                        contentType = ContentType.Application.Json,
                        text = content
                    )
                }
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

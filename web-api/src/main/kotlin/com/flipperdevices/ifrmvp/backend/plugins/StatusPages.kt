package com.flipperdevices.ifrmvp.backend.plugins

import com.flipperdevices.ifrmvp.backend.buildkonfig.BuildKonfig
import com.flipperdevices.ifrmvp.backend.model.exception.ErrorResponseModel
import com.flipperdevices.ifrmvp.backend.model.exception.UnhandledException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureStatusPages() {
    val logger = KtorSimpleLogger(BuildKonfig.GROUP)
    install(StatusPages) {
        unhandled { call ->
            logger.error("Unhandled exception: ${call.response}; ${call.request}")
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponseModel(serverException = UnhandledException),
            )
        }
    }
}

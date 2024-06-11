package com.flipperdevices.ifrmvp.backend

import com.flipperdevices.ifrmvp.backend.di.RootModule
import com.flipperdevices.ifrmvp.backend.plugins.configureHTTP
import com.flipperdevices.ifrmvp.backend.plugins.configureSecurity
import com.flipperdevices.ifrmvp.backend.plugins.configureSerialization
import com.flipperdevices.ifrmvp.backend.plugins.configureStatusPages
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.slf4j.Logger

internal fun Application.module(rootModule: RootModule, logger: Logger) {
    configureSerialization(rootModule.coreModule.json)
    configureHTTP()
    configureSecurity()
    configureStatusPages()
    routing {
        listOf(
            rootModule.statusModule.registry
        ).forEach { routeRegistry -> routeRegistry.register(this) }
    }
    logger.info("Started!")
}

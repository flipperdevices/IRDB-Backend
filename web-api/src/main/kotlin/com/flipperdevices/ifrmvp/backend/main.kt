@file:Suppress("Filename")

package com.flipperdevices.ifrmvp.backend

import com.flipperdevices.ifrmvp.backend.di.RootModule
import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.logging.KtorSimpleLogger

fun main() {
    val rootModule: RootModule = RootModule.Default()
    val logger = KtorSimpleLogger("Application")
    logger.info("Starting...")
    logger.info("Server is running on port ${EnvKonfig.FBACKEND_PORT}")
    embeddedServer(
        factory = Netty,
        module = { module(rootModule, logger) },
        port = EnvKonfig.FBACKEND_PORT
    ).start(wait = true)
}

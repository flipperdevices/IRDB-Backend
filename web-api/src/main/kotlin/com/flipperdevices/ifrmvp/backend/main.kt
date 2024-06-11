@file:Suppress("Filename")

package com.flipperdevices.ifrmvp.backend

import com.flipperdevices.ifrmvp.backend.di.RootModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.logging.KtorSimpleLogger

fun main() {
    val rootModule: RootModule = RootModule.Default()
    val logger = KtorSimpleLogger("Application")
    val envConfig = rootModule.buildKonfigModule
    logger.info("Starting...")
    logger.info("Server is running on ${envConfig.connection.host}:${envConfig.connection.port}")
    embeddedServer(
        factory = Netty,
        module = { module(rootModule, logger) },
        host = envConfig.connection.host,
        port = envConfig.connection.port
    ).start(wait = true)
}

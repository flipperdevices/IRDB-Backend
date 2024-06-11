package com.flipperdevices.ifrmvp.backend.envkonfig.di.factory

import com.flipperdevices.ifrmvp.backend.envkonfig.Connection
import com.flipperdevices.ifrmvp.backend.envkonfig.SystemExt
import java.net.InetAddress

internal object ConnectionFactory {
    private val logger = java.util.logging.Logger.getLogger("ConnectionFactory")
    fun create(): Connection {
        return Connection(
            port = SystemExt.getLocalPropertyOrNull("PORT")?.toIntOrNull() ?: run {
                logger.info("ENV value PORT not found; Used default")
                8080
            },
            host = SystemExt.getLocalPropertyOrNull("HOST") ?: run {
                logger.info("ENV value HOST not found; Used default")
                InetAddress.getLocalHost().hostAddress
            }
        )
    }
}

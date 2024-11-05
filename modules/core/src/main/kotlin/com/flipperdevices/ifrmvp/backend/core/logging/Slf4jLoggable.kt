package com.flipperdevices.ifrmvp.backend.core.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Slf4jLoggable(tag: String) : Loggable {
    private val logger: Logger = LoggerFactory.getLogger(tag)

    override fun info(msg: () -> String) {
        logger.debug(msg.invoke())
    }

    override fun debug(msg: () -> String) {
        logger.debug(msg.invoke())
    }

    override fun error(throwable: Throwable?, msg: () -> String) {
        if (throwable == null) logger.error(msg.invoke())
        else logger.error(msg.invoke(), throwable)
    }
}
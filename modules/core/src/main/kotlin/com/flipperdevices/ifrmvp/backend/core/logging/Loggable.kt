package com.flipperdevices.ifrmvp.backend.core.logging

interface Loggable {
    fun info(msg: () -> String)
    fun debug(msg: () -> String)
    fun error(throwable: Throwable? = null, msg: () -> String)

    class Default(tag: String) : Loggable by Slf4jLoggable(tag)
}


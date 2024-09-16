package com.flipperdevices.ifrmvp.backend.envkonfig

import org.slf4j.LoggerFactory

internal object KSystem {
    private val logger = LoggerFactory.getLogger("KSystem")!!
    fun getenvOrNull(key: String): String? {
        val value = System.getenv(key) ?: System.getProperty(key)
        if (value.isNullOrBlank()) {
            logger.warn("Could not found ENV property: $key")
            return null
        }
        return value
    }

    fun requireEnv(key: String): String {
        return getenvOrNull(key) ?: error { "Environment not found: $key" }
    }
}

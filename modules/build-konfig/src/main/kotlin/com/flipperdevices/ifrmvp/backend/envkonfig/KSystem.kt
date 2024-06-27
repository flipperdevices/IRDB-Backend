package com.flipperdevices.ifrmvp.backend.envkonfig

internal object KSystem {
    private val logger = java.util.logging.Logger.getLogger("KSystem")
    fun getenvOrNull(key: String): String? {
        val value = System.getenv(key) ?: System.getProperty(key)
        if (value.isNullOrBlank()) {
            logger.warning("Could not found ENV property: $key")
            return null
        }
        return value
    }
}

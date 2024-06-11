package com.flipperdevices.ifrmvp.backend.envkonfig

import java.io.File
import java.util.Properties

internal object SystemExt {
    fun getLocalPropertyOrNull(key: String): String? {
        val localProperties = File("../local.properties")
        if (!localProperties.exists()) return System.getenv(key)
        val properties = Properties().apply {
            load(localProperties.reader())
        }
        return properties.getProperty(key) ?: System.getenv(key)
    }

    fun requireLocalProperty(key: String): String {
        return getLocalPropertyOrNull(key) ?: error("Property $key not specified")
    }
}

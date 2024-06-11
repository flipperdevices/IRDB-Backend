package com.flipperdevices.ifrmvp.backend.core.di

import kotlinx.serialization.json.Json

interface CoreModule {
    val json: Json

    class Default : CoreModule {
        override val json: Json by lazy {
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        }
    }
}

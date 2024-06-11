package com.flipperdevices.ifrmvp.backend.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun Application.configureSerialization(json: Json) {
    install(ContentNegotiation) {
        json(json)
    }
}

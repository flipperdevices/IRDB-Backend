package com.flipperdevices.ifrmvp.backend.route.ui.presentation

import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object UiSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get json content of UI file"
        request {
            queryParameter<Long>("ifr_file_id") {
                description = "Unique id of ifr file"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Content of UI template"
                body<String> {
                    description = "Content of UI template"
                }
            }
        }
    }
}

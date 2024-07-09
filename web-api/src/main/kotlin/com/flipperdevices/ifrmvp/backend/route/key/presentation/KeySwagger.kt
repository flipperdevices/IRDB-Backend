package com.flipperdevices.ifrmvp.backend.route.key.presentation

import com.flipperdevices.ifrmvp.backend.model.IfrFileContentResponse
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object KeySwagger {
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
                description = "Content of IR"
                body<IfrFileContentResponse> {
                    description = "Content of IR file"
                }
            }
        }
    }
}

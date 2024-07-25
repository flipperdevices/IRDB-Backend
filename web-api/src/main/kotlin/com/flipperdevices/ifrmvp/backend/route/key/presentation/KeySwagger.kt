package com.flipperdevices.ifrmvp.backend.route.key.presentation

import com.flipperdevices.ifrmvp.backend.model.IfrFileContentResponse
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object KeySwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get content of .IR file"
        request {
            queryParameter<Long>("ifr_file_id") {
                description = "Unique id of .ir file"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Content of IR file"
                body<IfrFileContentResponse> {
                    description = "Content of IR file"
                }
            }
        }
    }
}

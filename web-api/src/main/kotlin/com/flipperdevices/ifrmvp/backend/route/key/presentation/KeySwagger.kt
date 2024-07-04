package com.flipperdevices.ifrmvp.backend.route.key.presentation

import com.flipperdevices.ifrmvp.backend.model.ErrorResponseModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object KeySwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get signal for testing configuration"
        request {
            body<SignalRequestModel> {
                description = "Required information to generate output signal"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Signal response"
                body<SignalResponseModel> {
                    description = "Signal to test ir signal"
                }
            }

            default {
                description = "Could not process request"
                body<ErrorResponseModel> {
                    description = "Could not get signal"
                }
            }
        }
    }
}

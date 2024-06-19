package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.model.SignalRequest
import com.flipperdevices.ifrmvp.backend.model.SignalResponse
import com.flipperdevices.ifrmvp.backend.model.exception.ErrorResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object SignalSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get signal for testing configuration"
        request {
            body<SignalRequest> {
                description = "Required information to generate output signal"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Signal response"
                body<SignalResponse> {
                    description = "Signal to test ir signal"
                }
            }

            HttpStatusCode.NotFound to {
                description = "Signal not found"
                body<ErrorResponseModel.NoSignal> {
                    description = "Could not find a signal based on give parameters"
                }
            }

            default {
                body<ErrorResponseModel.Unhandled> {
                    description = "Could not get signal"
                }
            }
        }
    }
}

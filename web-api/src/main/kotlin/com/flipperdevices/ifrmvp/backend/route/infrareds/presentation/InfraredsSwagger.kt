package com.flipperdevices.ifrmvp.backend.route.infrareds.presentation

import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import com.flipperdevices.ifrmvp.backend.model.ErrorResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object InfraredsSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get list of brand's infrared files"
        request {
            queryParameter<Long>("brand_id") {
                description = "Unique id of the brand"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Brand's infrared files fetched successfully"
                body<BrandsResponse> {
                    description = "List of brand's infrared files"
                }
            }

            default {
                description = "Could not process request"
                body<ErrorResponseModel> {
                    description = "Unhandled exception"
                }
            }
        }
    }
}

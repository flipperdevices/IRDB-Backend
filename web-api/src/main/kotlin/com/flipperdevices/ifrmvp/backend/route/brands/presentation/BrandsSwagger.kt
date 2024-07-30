package com.flipperdevices.ifrmvp.backend.route.brands.presentation

import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import com.flipperdevices.ifrmvp.backend.model.ErrorResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object BrandsSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get list of category's brands"
        request {
            queryParameter<Long>("category_id") {
                description = "Unique id of the category"
                required = true
            }
            queryParameter<String>("query") {
                description = "Entry string of brand name. " +
                        "For example, writing 'sam' - will return every brand that contains this string. " +
                        "Case insensitive."
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Category's brands fetched successfully"
                body<BrandsResponse> {
                    description = "List of category's brands"
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

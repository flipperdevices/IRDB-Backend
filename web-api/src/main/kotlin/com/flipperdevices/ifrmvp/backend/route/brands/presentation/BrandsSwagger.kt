package com.flipperdevices.ifrmvp.backend.route.brands.presentation

import com.flipperdevices.ifrmvp.backend.model.BrandsResponse
import com.flipperdevices.ifrmvp.backend.model.exception.ErrorResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object BrandsSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get list of category's manufacturers"
        request {
            queryParameter<String>("category_name") {
                description = "Unique name of the category"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Manufacturers fetched successfully"
                body<BrandsResponse> {
                    description = "List of manufacturers"
                }
            }

            HttpStatusCode.NotFound to {
                description = "Category not found"
                body<ErrorResponseModel.CategoryNotFound>()
            }

            default {
                body<ErrorResponseModel.Unhandled> {
                    description = "Unhandled exception"
                }
            }
        }
    }
}

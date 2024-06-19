package com.flipperdevices.ifrmvp.backend.route.categories.presentation

import com.flipperdevices.ifrmvp.backend.model.CategoriesResponse
import com.flipperdevices.ifrmvp.backend.model.exception.ErrorResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

internal object CategoriesSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Get list of available categories"
        response {
            HttpStatusCode.OK to {
                description = "Categories fetched successfully"
                body<CategoriesResponse> {
                    description = "List of available categories"
                }
            }

            default {
                body<ErrorResponseModel.Unhandled> {
                    description = "Unhandled exception"
                }
            }
        }
    }
}

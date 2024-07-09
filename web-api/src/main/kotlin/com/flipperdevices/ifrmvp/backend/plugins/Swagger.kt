package com.flipperdevices.ifrmvp.backend.plugins

import com.flipperdevices.ifrmvp.backend.buildkonfig.BuildKonfig
import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.data.SwaggerUiSort
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.github.smiley4.schemakenerator.serialization.processKotlinxSerialization
import io.github.smiley4.schemakenerator.swagger.compileReferencingRoot
import io.github.smiley4.schemakenerator.swagger.data.TitleType
import io.github.smiley4.schemakenerator.swagger.generateSwaggerSchema
import io.github.smiley4.schemakenerator.swagger.withAutoTitle
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureSwagger() {
    routing {
        // Create a route for the swagger-ui using the openapi-spec at "/api.json".
        // This route will not be included in the spec.
        route("swagger") {
            swaggerUI(apiUrl = "/api.json")
        }
        // Create a route for the openapi-spec file.
        // This route will not be included in the spec.
        route("api.json") {
            openApiSpec()
        }
    }

    install(SwaggerUI) {
        schemas {
            generator = { type ->
                type
                    // process type using kotlinx-serialization instead of reflection
                    // requires additional dependency "io.github.smiley4:schema-kenerator-kotlinx-serialization:<VERSION>"
                    // see https://github.com/SMILEY4/schema-kenerator for more information
                    .processKotlinxSerialization()
                    .generateSwaggerSchema()
                    .withAutoTitle(TitleType.SIMPLE)
                    .compileReferencingRoot()
            }
        }
        info {
            title = BuildKonfig.PROJECT_NAME
            version = BuildKonfig.VERSION_NAME
            summary = "API for Flipper IFR"
            description = BuildKonfig.PROJECT_DESC
            termsOfService = "https://somesite.com"
            contact {
                name = "Some Name"
                url = "https://someurl.corp"
                email = "somemail@mail.mail"
            }
        }
        server {
            url = "${EnvKonfig.FBACKEND_HOST}:${EnvKonfig.FBACKEND_PORT}"
            description = "Web-Api server"
            variable("version") {
                default = BuildKonfig.VERSION_NAME
                enum = listOf(BuildKonfig.VERSION_NAME)
            }
        }
        swagger {
            displayOperationId = true
            showTagFilterInput = true
            sort = SwaggerUiSort.HTTP_METHOD
            withCredentials = false
        }
    }
}

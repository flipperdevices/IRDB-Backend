package com.flipperdevices.ifrmvp.backend.plugins

import com.flipperdevices.ifrmvp.backend.buildkonfig.BuildKonfig
import com.flipperdevices.ifrmvp.backend.envkonfig.di.BuildKonfigModule
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
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
            url =
                "${BuildKonfigModule.Default.connection.host}/${BuildKonfigModule.Default.connection.port}"
            description = "Web-Api server"
            variable("version") {
                default = BuildKonfig.VERSION_NAME
                enum = listOf(BuildKonfig.VERSION_NAME)
            }
        }
    }
}

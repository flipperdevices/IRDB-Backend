package com.flipperdevices.ifrmvp.backend.route.signal.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.ifrmvp.backend.model.SignalRequestModel
import com.flipperdevices.ifrmvp.backend.model.SignalResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import kotlin.random.Random

internal class SignalRouteRegistry : RouteRegistry {

    private fun Routing.statusRoute() {
        post(
            path = "signal",
            builder = { with(SignalSwagger) { createSwaggerDefinition() } },
            body = {
                val signalRequestModel = context.receive<SignalRequestModel>()
                val responseModel = SignalResponseModel(
                    signalModel = SignalModel(
                        id = -1,
                        irFileId = -1,
                        brandId = -1,
                        categoryId = -1,
                        name = "NAME",
                        type = "TYPE"
                    ).takeIf { Random.nextBoolean() },
                    hasNext = Random.nextBoolean(),
                    buttonInfo = SignalResponseModel.ButtonInfo(
                        backgroundColor = 0xFFF63F3F,
                        tintColor = 0xFFFFFFFF,
                        iconType = SignalResponseModel.ButtonInfo.IconType.POWER
                    )
                )
                context.respond(responseModel)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

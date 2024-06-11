package com.flipperdevices.ifrmvp.backend.route.categories.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.CategoriesRequestBody
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType
import com.flipperdevices.ifrmvp.backend.model.PagedModel
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

internal class CategoriesRouteRegistry : RouteRegistry {

    private fun Routing.statusRoute() {
        post("categories") {
            @Suppress("UnusedPrivateProperty")
            val categoriesRequestBody = call.receive<CategoriesRequestBody>()

            val responseModel = PagedModel(
                page = 0,
                maxPages = 1,
                items = listOf(
                    DeviceCategory("TV", DeviceCategoryType.TV),
                    DeviceCategory("Air Conditioner", DeviceCategoryType.AIR_CONDITIONER),
                    DeviceCategory("Set-top Box", DeviceCategoryType.SET_TOP_BOX),
                    DeviceCategory("Camera", DeviceCategoryType.CAMERA),
                    DeviceCategory("Fan", DeviceCategoryType.FAN),
                    DeviceCategory("A/V Receiver", DeviceCategoryType.A_V_RECEIVER),
                    DeviceCategory("DVD Player", DeviceCategoryType.DVD_PLAYER),
                    DeviceCategory("Smart Box", DeviceCategoryType.SMART_BOX),
                    DeviceCategory("Projector", DeviceCategoryType.PROJECTOR),
                )
            )
            context.respond(responseModel)
        }
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

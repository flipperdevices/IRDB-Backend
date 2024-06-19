package com.flipperdevices.ifrmvp.backend.route.categories.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.model.CategoriesResponse
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class CategoriesRouteRegistry : RouteRegistry {

    private fun Routing.statusRoute() {
        post(
            path = "categories",
            builder = { with(CategoriesSwagger) { createSwaggerDefinition() } },
            body = {
                val categoriesResponse = CategoriesResponse(
                    categories = listOf(
                        DeviceCategory(0, "TV", DeviceCategoryType.TV),
                        DeviceCategory(1, "Air Conditioner", DeviceCategoryType.AIR_CONDITIONER),
                        DeviceCategory(2, "Set-top Box", DeviceCategoryType.SET_TOP_BOX),
                        DeviceCategory(3, "Camera", DeviceCategoryType.CAMERA),
                        DeviceCategory(4, "Fan", DeviceCategoryType.FAN),
                        DeviceCategory(5, "A/V Receiver", DeviceCategoryType.A_V_RECEIVER),
                        DeviceCategory(6, "DVD Player", DeviceCategoryType.DVD_PLAYER),
                        DeviceCategory(7, "Smart Box", DeviceCategoryType.SMART_BOX),
                        DeviceCategory(8, "Projector", DeviceCategoryType.PROJECTOR),
                    )
                )
                context.respond(categoriesResponse)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object CameraCategoryConfigGenerator {
    @Suppress("LongMethod")
    fun generate(): CategoryConfiguration {
        var i = 0
        return CategoryConfiguration(
            orders = listOf(
                CategoryConfiguration.OrderModel(
                    data = PowerButtonData(),
                    message = "Does the camera turn on/off?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the camera taken a photo?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CAMERA),
                    key = DeviceKey.SHUTTER,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the camera zoomed in?",
                    data = IconButtonData(iconId = IconButtonData.IconType.ZOOM_IN),
                    key = DeviceKey.ZOOM_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the camera zoomed out?",
                    data = IconButtonData(iconId = IconButtonData.IconType.ZOOM_OUT),
                    key = DeviceKey.ZOOM_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the menu shown?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MENU),
                    key = DeviceKey.MENU,
                    index = ++i,
                ),
            )
        )
    }

    fun generate(categoryType: CategoryType): CategoryConfiguration {
        val configuration = generate()
        return CategoryConfiguration(
            orders = configuration.orders.filter {
                it.key.getAllowedCategories().contains(categoryType)
            }
        )
    }
}

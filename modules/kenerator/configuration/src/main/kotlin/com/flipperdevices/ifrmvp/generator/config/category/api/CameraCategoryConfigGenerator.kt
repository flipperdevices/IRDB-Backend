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
                    message = "Does %s turns on?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it take photo?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CAMERA),
                    key = DeviceKey.SHUTTER,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it zoom in?",
                    data = TextButtonData(text = "Z+"),
                    key = DeviceKey.ZOOM_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it zoom out?",
                    data = TextButtonData(text = "Z-"),
                    key = DeviceKey.ZOOM_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is menu opened?",
                    data = TextButtonData(text = "MENU"),
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

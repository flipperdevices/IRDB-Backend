package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object TvsCategoryConfigGenerator {
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
                    message = "Does it increase volume?",
                    data = TextButtonData(text = "V+"),
                    key = DeviceKey.VOL_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it decrease volume?",
                    data = TextButtonData(text = "V-"),
                    key = DeviceKey.VOL_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it mute?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MUTE),
                    key = DeviceKey.MUTE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it sho next channel?",
                    data = TextButtonData(text = "CH+"),
                    key = DeviceKey.VOL_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it show previous channel?",
                    data = TextButtonData(text = "CH-"),
                    key = DeviceKey.VOL_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it pause?",
                    data = TextButtonData(text = "PAUSE"),
                    key = DeviceKey.PAUSE,
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
                    message = "Does it increase brightness?",
                    data = TextButtonData(text = "BR-"),
                    key = DeviceKey.BRIGHTNESS_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it decrease brightness?",
                    data = TextButtonData(text = "BR-"),
                    key = DeviceKey.BRIGHTNESS_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it record?",
                    data = TextButtonData(text = "RC"),
                    key = DeviceKey.RECORD,
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

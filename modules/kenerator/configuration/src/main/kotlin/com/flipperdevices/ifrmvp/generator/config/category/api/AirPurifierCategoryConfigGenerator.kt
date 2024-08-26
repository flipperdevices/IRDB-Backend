package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object AirPurifierCategoryConfigGenerator {
    @Suppress("LongMethod")
    fun generate(): CategoryConfiguration {
        var i  =0
        return CategoryConfiguration(
            orders = listOf(
                CategoryConfiguration.OrderModel(
                    data = PowerButtonData(),
                    message = "Does %s turns on?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is it oscillated?",
                    data = TextButtonData(text = "OSC"),
                    key = DeviceKey.OSCILLATE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is Fan speed increased??",
                    data = TextButtonData(text = "FS+"),
                    key = DeviceKey.FAN_SPEED_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is Fan speed decreased?",
                    data = TextButtonData(text = "FS-"),
                    key = DeviceKey.FAN_SPEED_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it swing?",
                    data = TextButtonData(text = "SW"),
                    key = DeviceKey.SWING,
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

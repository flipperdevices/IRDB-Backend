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
                    data = IconButtonData(iconId = IconButtonData.IconType.OSCILLATE),
                    key = DeviceKey.OSCILLATE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is Fan speed increased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.FAN_SPEED_UP),
                    key = DeviceKey.FAN_SPEED_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is Fan speed decreased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.FAN_SPEED_DOWN),
                    key = DeviceKey.FAN_SPEED_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it swing?",
                    data = IconButtonData(iconId = IconButtonData.IconType.SWING),
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

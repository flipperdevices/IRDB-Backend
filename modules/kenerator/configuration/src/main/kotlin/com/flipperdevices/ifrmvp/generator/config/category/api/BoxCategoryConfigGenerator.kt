package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object BoxCategoryConfigGenerator {
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
                    message = "Does volume go up?",
                    data = IconButtonData(iconId = IconButtonData.IconType.VOL_UP),
                    key = DeviceKey.VOL_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does volume go down?",
                    data = IconButtonData(iconId = IconButtonData.IconType.VOL_DOWN),
                    key = DeviceKey.VOL_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is volume muted?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MUTE),
                    key = DeviceKey.MUTE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is next channel shown?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CH_UP),
                    key = DeviceKey.CH_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is previous channel shown?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CH_DOWN),
                    key = DeviceKey.CH_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is menu opened?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MENU),
                    key = DeviceKey.MENU,
                    index = ++i,
                )
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

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
                    data = IconButtonData(iconId = IconButtonData.IconType.VOL_UP),
                    key = DeviceKey.VOL_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it decrease volume?",
                    data = IconButtonData(iconId = IconButtonData.IconType.VOL_DOWN),
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
                    data = IconButtonData(iconId = IconButtonData.IconType.CH_UP),
                    key = DeviceKey.CH_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it show previous channel?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CH_DOWN),
                    key = DeviceKey.CH_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it pause?",
                    data = IconButtonData(iconId = IconButtonData.IconType.PAUSE),
                    key = DeviceKey.PAUSE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it zoom in?",
                    data = IconButtonData(iconId = IconButtonData.IconType.ZOOM_IN),
                    key = DeviceKey.ZOOM_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it zoom out?",
                    data = IconButtonData(iconId = IconButtonData.IconType.ZOOM_OUT),
                    key = DeviceKey.ZOOM_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it increase brightness?",
                    data = IconButtonData(iconId = IconButtonData.IconType.BRIGHT_MORE),
                    key = DeviceKey.BRIGHTNESS_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it decrease brightness?",
                    data = IconButtonData(iconId = IconButtonData.IconType.BRIGHT_LESS),
                    key = DeviceKey.BRIGHTNESS_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it record?",
                    data = IconButtonData(iconId = IconButtonData.IconType.RECORD),
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

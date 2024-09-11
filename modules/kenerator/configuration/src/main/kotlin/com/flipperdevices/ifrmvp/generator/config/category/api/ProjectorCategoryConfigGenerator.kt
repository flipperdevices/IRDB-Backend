package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object ProjectorCategoryConfigGenerator {
    @Suppress("LongMethod")
    fun generate(): CategoryConfiguration {
        var i = 0
        return CategoryConfiguration(
            orders = listOf(
                CategoryConfiguration.OrderModel(
                    data = PowerButtonData(),
                    message = "Does the projector turn on/off?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the volume increased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.VOL_UP),
                    key = DeviceKey.VOL_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the volume decreased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.VOL_DOWN),
                    key = DeviceKey.VOL_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the volume been muted?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MUTE),
                    key = DeviceKey.MUTE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the projector zoomed in?",
                    data = IconButtonData(iconId = IconButtonData.IconType.ZOOM_IN),
                    key = DeviceKey.ZOOM_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the projector zoomed out?",
                    data = IconButtonData(iconId = IconButtonData.IconType.ZOOM_OUT),
                    key = DeviceKey.ZOOM_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the brightness decreased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.BRIGHT_MORE),
                    key = DeviceKey.BRIGHTNESS_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the TV started a video recording?",
                    data = IconButtonData(iconId = IconButtonData.IconType.BRIGHT_LESS),
                    key = DeviceKey.BRIGHTNESS_DOWN,
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

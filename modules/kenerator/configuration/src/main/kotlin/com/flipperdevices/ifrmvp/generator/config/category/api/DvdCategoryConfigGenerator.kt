package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object DvdCategoryConfigGenerator {
    @Suppress("LongMethod")
    fun generate(): CategoryConfiguration {
        var i = 0
        return CategoryConfiguration(
            orders = listOf(
                CategoryConfiguration.OrderModel(
                    data = PowerButtonData(),
                    message = "Does the DVD player turn on/off?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has it switched to the next channel?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CH_UP),
                    key = DeviceKey.CH_UP,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has it switched to the previous channel?",
                    data = IconButtonData(iconId = IconButtonData.IconType.CH_DOWN),
                    key = DeviceKey.CH_DOWN,
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
                    message = "Has the DVD player ejected the disk?",
                    data = IconButtonData(iconId = IconButtonData.IconType.EJECT),
                    key = DeviceKey.EJECT,
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

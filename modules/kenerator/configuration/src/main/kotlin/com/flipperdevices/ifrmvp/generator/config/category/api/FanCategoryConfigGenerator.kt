package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object FanCategoryConfigGenerator {
    @Suppress("LongMethod")
    fun generate(): CategoryConfiguration {
        var i = 0
        return CategoryConfiguration(
            orders = listOf(
                CategoryConfiguration.OrderModel(
                    data = PowerButtonData(),
                    message = "Does the fan turn on/off?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the wind speed changed?",
                    data = IconButtonData(iconId = IconButtonData.IconType.WIND_SPEED),
                    key = DeviceKey.WIND_SPEED,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has wind shake been enabled?",
                    data = IconButtonData(iconId = IconButtonData.IconType.SHAKE_WIND),
                    key = DeviceKey.SHAKE_WIND,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the fan mode changed?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MODE),
                    key = DeviceKey.MODE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does the fan light turn on/off?",
                    data = IconButtonData(iconId = IconButtonData.IconType.LIGHT),
                    key = DeviceKey.LIGHT,
                    index = ++i,
                ),

                CategoryConfiguration.OrderModel(
                    message = "Has it increased speed?",
                    data = IconButtonData(iconId = IconButtonData.IconType.FAN_SPEED_UP),
                    key = DeviceKey.FAN_SPEED_UP,
                    index = ++i,
                ),

                CategoryConfiguration.OrderModel(
                    message = "Has it decreased speed?",
                    data = IconButtonData(iconId = IconButtonData.IconType.FAN_SPEED_DOWN),
                    key = DeviceKey.FAN_SPEED_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the speed changed?",
                    data = IconButtonData(iconId = IconButtonData.IconType.WIND_SPEED),
                    key = DeviceKey.FAN_SPEED,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is the fan oscillating (swinging)?",
                    data = IconButtonData(iconId = IconButtonData.IconType.OSCILLATE),
                    key = DeviceKey.OSCILLATE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the fan been muted?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MUTE),
                    key = DeviceKey.MUTE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the timer duration changed?",
                    data = IconButtonData(iconId = IconButtonData.IconType.TIMER),
                    key = DeviceKey.TIMER,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the timer duration increased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.TIMER_ADD),
                    key = DeviceKey.TIMER_ADD,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the timer duration decreased?",
                    data = IconButtonData(iconId = IconButtonData.IconType.TIMER_REDUCE),
                    key = DeviceKey.TIMER_REDUCE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is the fan swinging?",
                    data = IconButtonData(iconId = IconButtonData.IconType.SWING),
                    key = DeviceKey.SWING,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Has the fan switched to sleep mode?",
                    data = IconButtonData(iconId = IconButtonData.IconType.SLEEP),
                    key = DeviceKey.SLEEP,
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

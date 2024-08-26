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
                    message = "Does %s turns on?",
                    key = DeviceKey.PWR,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it change wind speed?",
                    data = TextButtonData(text = "WSP"),
                    key = DeviceKey.WIND_SPEED,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it enable wind shake?",
                    data = TextButtonData(text = "WSH"),
                    key = DeviceKey.SHAKE_WIND,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it change mode?",
                    data = TextButtonData(text = "MODE"),
                    key = DeviceKey.MODE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it change lights?",
                    data = TextButtonData(text = "LIGHT"),
                    key = DeviceKey.LIGHT,
                    index = ++i,
                ),

                CategoryConfiguration.OrderModel(
                    message = "Does it increase fan speed?",
                    data = TextButtonData(text = "SP+"),
                    key = DeviceKey.FAN_SPEED_UP,
                    index = ++i,
                ),

                CategoryConfiguration.OrderModel(
                    message = "Does it decrease fan speed?",
                    data = TextButtonData(text = "SP-"),
                    key = DeviceKey.FAN_SPEED_DOWN,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is speed changed?",
                    data = TextButtonData(text = "SP"),
                    key = DeviceKey.FAN_SPEED,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is speed oscillated?",
                    data = TextButtonData(text = "OSC"),
                    key = DeviceKey.OSCILLATE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is it muted?",
                    data = IconButtonData(iconId = IconButtonData.IconType.MUTE),
                    key = DeviceKey.MUTE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it change mode?",
                    data = TextButtonData(text = "MODE"),
                    key = DeviceKey.MODE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is timer changed?",
                    data = TextButtonData(text = "T"),
                    key = DeviceKey.TIMER,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is timer increased?",
                    data = TextButtonData(text = "T+"),
                    key = DeviceKey.TIMER_ADD,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Is timer decreased?",
                    data = TextButtonData(text = "T-"),
                    key = DeviceKey.TIMER_REDUCE,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it swing?",
                    data = TextButtonData(text = "SWING"),
                    key = DeviceKey.SWING,
                    index = ++i,
                ),
                CategoryConfiguration.OrderModel(
                    message = "Does it go to sleep?",
                    data = TextButtonData(text = "SLEEP"),
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

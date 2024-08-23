package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData

object AllCategoryConfigGenerator {
    /**
     * Generate all categories configurations
     *
     * Some categories wil have the same orders. We need [DeviceKeyExt.getAllowedCategories]
     * to understand for which category we need keys
     */
    @Suppress("LongMethod")
    fun generate(): CategoryConfiguration {
        return CategoryConfiguration(
            orders = DeviceKey.entries.mapIndexed { index, key ->
                when (key) {
                    DeviceKey.PWR -> CategoryConfiguration.OrderModel(
                        data = PowerButtonData(),
                        message = "Does %s turns on?",
                        key = key,
                        index = index,
                    )

                    DeviceKey.VOL_UP -> CategoryConfiguration.OrderModel(
                        message = "Does volume go up?",
                        data = TextButtonData(text = "+"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.VOL_DOWN -> CategoryConfiguration.OrderModel(
                        message = "Does volume go down?",
                        data = TextButtonData(text = "-"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.CH_UP -> CategoryConfiguration.OrderModel(
                        message = "Does channel go next?",
                        data = TextButtonData(text = "+"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.CH_DOWN -> CategoryConfiguration.OrderModel(
                        message = "Does channel go previous?",
                        data = TextButtonData(text = "-"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.FOCUS_MORE -> CategoryConfiguration.OrderModel(
                        message = "Does key focuses more?",
                        data = TextButtonData(text = "+"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.FOCUS_LESS -> CategoryConfiguration.OrderModel(
                        message = "Does key focuses less?",
                        data = TextButtonData(text = "-"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.ZOOM_UP -> CategoryConfiguration.OrderModel(
                        message = "Does key zoom more?",
                        data = TextButtonData(text = "+"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.ZOOM_DOWN -> CategoryConfiguration.OrderModel(
                        message = "Does key zoom less?",
                        data = TextButtonData(text = "-"),
                        key = key,
                        index = index,
                    )

                    DeviceKey.SHUTTER -> CategoryConfiguration.OrderModel(
                        message = "Did %s take a photo?",
                        data = IconButtonData(iconId = IconButtonData.IconType.CAMERA),
                        key = key,
                        index = index,
                    )

                    else -> CategoryConfiguration.OrderModel(
                        message = "Does it do something?",
                        data = TextButtonData(text = key.name),
                        key = key,
                        index = index,
                    )
                }
            }
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

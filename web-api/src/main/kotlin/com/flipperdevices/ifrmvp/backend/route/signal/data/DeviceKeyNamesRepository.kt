package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider

interface DeviceKeyNamesRepository {
    fun getDeviceKeyNames(order: CategoryConfiguration.OrderModel): List<String>
}

object InstantDeviceKeyNamesRepository : DeviceKeyNamesRepository {
    override fun getDeviceKeyNames(order: CategoryConfiguration.OrderModel): List<String> {
        return AnyDeviceKeyNamesProvider.getKeyNames(order.key)
    }
}

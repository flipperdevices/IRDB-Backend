package com.flipperdevices.ifrmvp.generator.config.device.api

import com.flipperdevices.ifrmvp.backend.model.DeviceKey

interface DeviceKeyNamesProvider {
    fun getKeyNames(key: DeviceKey): List<String>

    companion object {
        fun DeviceKeyNamesProvider.getKey(keyName: String): DeviceKey? {
            return DeviceKey.entries
                .firstOrNull { deviceKey ->
                    getKeyNames(deviceKey).any { it.equals(keyName, true) }
                }
        }
    }
}

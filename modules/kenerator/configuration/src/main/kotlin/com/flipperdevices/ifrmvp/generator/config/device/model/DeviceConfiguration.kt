package com.flipperdevices.ifrmvp.generator.config.device.model

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DeviceConfiguration(
    @SerialName("key_map")
    val keyMap: Map<DeviceKey, KeyInstance>
) {
    @Serializable
    data class KeyInstance(
        @SerialName("key_name")
        val keyName: String
    )
}

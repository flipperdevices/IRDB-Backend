package com.flipperdevices.ifrmvp.backend.model

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

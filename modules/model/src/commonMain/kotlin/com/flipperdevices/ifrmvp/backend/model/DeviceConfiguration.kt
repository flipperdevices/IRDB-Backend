package com.flipperdevices.ifrmvp.backend.model

import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DeviceConfiguration(
    @SerialName("key_map")
    val keyMap: Map<DeviceKey, IfrKeyIdentifier>
)
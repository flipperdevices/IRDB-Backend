package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class DeviceCategory(
    val name: String,
    val type: DeviceCategoryType
)

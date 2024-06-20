package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("DeviceCategoryType")
enum class DeviceCategoryType {
    TV,
    AIR_CONDITIONER,
    SET_TOP_BOX,
    CAMERA,
    FAN,
    A_V_RECEIVER,
    DVD_PLAYER,
    SMART_BOX,
    PROJECTOR,
    UNKNOWN
}

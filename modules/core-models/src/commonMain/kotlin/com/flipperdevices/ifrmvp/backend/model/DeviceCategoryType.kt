package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
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

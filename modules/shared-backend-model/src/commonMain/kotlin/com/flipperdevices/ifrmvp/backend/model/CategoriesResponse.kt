package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("CategoriesResponse")
data class CategoriesResponse(
    val categories: List<DeviceCategory>
)

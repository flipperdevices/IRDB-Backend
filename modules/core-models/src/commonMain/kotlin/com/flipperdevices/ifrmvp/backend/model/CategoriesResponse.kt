package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    val categories: List<DeviceCategory>
)

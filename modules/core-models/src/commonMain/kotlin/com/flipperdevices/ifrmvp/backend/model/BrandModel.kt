package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("BrandModel")
class BrandModel(
    val id: Long,
    val name: String,
)

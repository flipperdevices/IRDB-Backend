package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("BrandsResponse")
data class BrandsResponse(
    val brands: List<BrandModel>
)

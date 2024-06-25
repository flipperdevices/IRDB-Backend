package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class IfrFileModel(
    val id: Long,
    val categoryId: Long,
    val brandId: Long
)

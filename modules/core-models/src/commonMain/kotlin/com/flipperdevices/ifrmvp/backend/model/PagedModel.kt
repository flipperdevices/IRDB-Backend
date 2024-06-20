package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PagedModel")
data class PagedModel<T>(
    val page: Int,
    val maxPages: Int,
    val items: List<T>
)

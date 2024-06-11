package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class PagedModel<T>(
    val page: Int,
    val maxPages: Int,
    val items: List<T>
)

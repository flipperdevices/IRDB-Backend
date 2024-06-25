package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class SignalModel(
    val id: Long,
    val irFileId: Long,
    val brandId: Long,
    val categoryId: Long,
    val name: String,
    val type: String,
    val protocol: String? = null,
    val address: String? = null,
    val command: String? = null,
    val frequency: String? = null,
    val dutyCycle: String? = null,
    val data: String? = null
)

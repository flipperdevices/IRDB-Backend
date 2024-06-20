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
    val address: ByteArray? = null,
    val command: ByteArray? = null,
    val frequency: Long? = null,
    val dutyCycle: Float? = null,
    val data: ByteArray? = null
)

package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class IfrSignal(
    val name: String,
    val type: Type,
    val protocol: String,
    val address: String,
    val command: String,
    val frequency: Long,
    val dutyCycle: Float,
    val data: ByteArray
) {
    @Serializable
    enum class Type {
        RAW, PARSED
    }
}

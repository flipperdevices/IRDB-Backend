package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("IfrSignal")
data class IfrSignal(
    val name: String,
    val type: IfrSignalType,
    val protocol: String,
    val address: String,
    val command: String,
    val frequency: Long,
    val dutyCycle: Float,
    val data: List<Byte>
)

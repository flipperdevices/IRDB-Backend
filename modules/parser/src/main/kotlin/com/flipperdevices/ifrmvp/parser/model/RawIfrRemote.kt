package com.flipperdevices.ifrmvp.parser.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RawIfrRemote(
    val name: String,
    val type: String,
    val protocol: String?,
    val address: String?,
    val command: String?,
    val frequency: String?,
    val dutyCycle: String?,
    val data: String?,
)

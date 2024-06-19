package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class SignalResponse(
    val signal: IfrSignal,
    val hasNext: Boolean
)

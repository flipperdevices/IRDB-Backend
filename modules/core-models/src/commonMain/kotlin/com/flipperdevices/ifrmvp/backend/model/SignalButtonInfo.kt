package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class SignalButtonInfo(
    val backgroundColor: Long,
    val tintColor: Long,
    val iconUrl: String? = null,
    val iconType: SignalButtonIconType? = null,
    val text: String? = null
)

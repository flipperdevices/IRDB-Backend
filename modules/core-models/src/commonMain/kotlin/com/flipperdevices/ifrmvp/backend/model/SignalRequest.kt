package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class SignalRequest(
    val successfulSignalIds: List<Long>,
    val failedSignalIds: List<Long>,
    val countryCode: String,
    val languageCode: String,
    val city: String,
    val manufacturerId: Long,
    val categoryId: Long
)

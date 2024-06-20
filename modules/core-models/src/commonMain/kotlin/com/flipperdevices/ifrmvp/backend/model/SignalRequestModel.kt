package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class SignalRequestModel(
    val successSignalsIds: List<Long>,
    val failedSignalsIds: List<Long>,
    val categoryId: Long,
    val brandId: Long
)

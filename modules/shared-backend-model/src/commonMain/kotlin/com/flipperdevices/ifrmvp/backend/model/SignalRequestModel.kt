package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class SignalRequestModel(
    val successResults: List<SignalResultData>,
    val failedResults: List<SignalResultData>,
    val categoryId: Long,
    val brandId: Long
) {
    @Serializable
    data class SignalResultData(
        val signalId: Long,
        val ifrFileId: Long
    )
}

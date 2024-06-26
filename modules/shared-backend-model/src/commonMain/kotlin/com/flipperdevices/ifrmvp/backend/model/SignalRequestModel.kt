package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class SignalRequestModel(
    val successResults: List<SignalResultData> = emptyList(),
    val failedResults: List<SignalResultData> = emptyList(),
    val categoryId: Long,
    val brandId: Long
) {
    @Serializable
    data class SignalResultData(
        val signalId: Long,
        val ifrFileId: Long
    )
}

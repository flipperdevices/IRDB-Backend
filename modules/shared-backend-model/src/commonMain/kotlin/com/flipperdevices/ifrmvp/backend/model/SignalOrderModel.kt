package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class SignalOrderModel(
    val categoryId: Long,
    val brandId: Long,
    val ifrFile: IfrFileModel,
    val signalModel: SignalModel,
    val dataType: String,
    val dataIconId: String?,
    val dataText: String?
)

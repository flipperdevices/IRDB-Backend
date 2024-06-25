package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class SignalOrderModel(
    val id: Long,
    val categoryId: Long,
    val brandId: Long,
    val ifrFile: IfrFileModel,
    val signalModel: SignalModel,
    val order: Int,
    val dataTye: String,
    val dataIconId: String?,
    val dateText: String?
)

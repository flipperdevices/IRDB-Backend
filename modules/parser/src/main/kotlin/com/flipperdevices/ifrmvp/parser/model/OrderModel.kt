package com.flipperdevices.ifrmvp.parser.model

import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import com.flipperdevices.ifrmvp.model.serialization.ButtonDataSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OrderModel(
    @SerialName("data")
    @Serializable(with = ButtonDataSerializer::class)
    val data: ButtonData,
    @Serializable
    val message: String
)

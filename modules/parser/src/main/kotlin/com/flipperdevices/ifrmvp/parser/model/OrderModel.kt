package com.flipperdevices.ifrmvp.parser.model

import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import com.flipperdevices.ifrmvp.parser.serialization.OrderModelSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(OrderModelSerializer::class)
data class OrderModel(
    @SerialName("data")
    val data: ButtonData,
)

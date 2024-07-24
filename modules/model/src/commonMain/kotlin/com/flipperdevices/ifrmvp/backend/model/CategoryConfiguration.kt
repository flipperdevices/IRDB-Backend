package com.flipperdevices.ifrmvp.backend.model

import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import com.flipperdevices.ifrmvp.model.serialization.ButtonDataSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CategoryConfiguration(
    @SerialName("orders")
    val orders: List<OrderModel>
) {
    @Serializable
    data class OrderModel(
        @SerialName("message")
        val message: String,
        @SerialName("index")
        val index: Int,
        @SerialName("key")
        val key: DeviceKey,
        @Serializable(ButtonDataSerializer::class)
        @SerialName("data")
        val data: ButtonData
    )
}

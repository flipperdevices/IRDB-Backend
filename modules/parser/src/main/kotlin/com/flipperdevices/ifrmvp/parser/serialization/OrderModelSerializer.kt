package com.flipperdevices.ifrmvp.parser.serialization

import com.flipperdevices.ifrmvp.model.serialization.ButtonDataDecoder
import com.flipperdevices.ifrmvp.model.serialization.ButtonDataEncoder
import com.flipperdevices.ifrmvp.parser.model.OrderModel
import com.flipperdevices.ifrmvp.parser.model.RawOrderModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal object OrderModelSerializer : KSerializer<OrderModel> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = "Flipper.OrderModel",
        builderAction = {
            element<JsonObject>("data")
        }
    )

    private val json: Json = Json {
        encodeDefaults = true
    }

    override fun deserialize(decoder: Decoder): OrderModel {
        val rawIfrButton = RawOrderModel.serializer().deserialize(decoder)
        return OrderModel(
            data = ButtonDataDecoder(json).decodeFromJsonObject(rawIfrButton.data),
        )
    }

    override fun serialize(encoder: Encoder, value: OrderModel) {
        val rawIfrButton = RawOrderModel(
            data = ButtonDataEncoder(json).encodeToJsonObject(value.data),
        )
        RawOrderModel.serializer().serialize(encoder, rawIfrButton)
    }
}

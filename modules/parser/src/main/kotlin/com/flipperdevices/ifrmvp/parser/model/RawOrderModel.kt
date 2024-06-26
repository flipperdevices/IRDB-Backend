package com.flipperdevices.ifrmvp.parser.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RawOrderModel(
    val data: JsonObject
)

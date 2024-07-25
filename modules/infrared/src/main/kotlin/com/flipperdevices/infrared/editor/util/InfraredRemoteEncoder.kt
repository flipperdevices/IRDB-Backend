package com.flipperdevices.infrared.editor.util

import com.flipperdevices.infrared.editor.model.InfraredRemote
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object InfraredRemoteEncoder {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    fun encode(remote: InfraredRemote): ByteArray {
        return json.encodeToString(remote).encodeToByteArray()
    }

    fun decode(byteArray: ByteArray): InfraredRemote {
        return json.decodeFromString(byteArray.decodeToString())
    }
}
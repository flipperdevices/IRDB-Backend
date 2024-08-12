package com.flipperdevices.infrared.editor.util

import com.flipperdevices.ifrmvp.backend.model.SignalModel
import com.flipperdevices.infrared.editor.model.InfraredRemote
import kotlin.jvm.internal.Ref.FloatRef
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object InfraredRemoteEncoder {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = false
        classDiscriminator = "_discriminator"
    }

    private fun InfraredRemote.toFlipperRemote(): SignalModel.FlipperRemote {
        val raw = this as? InfraredRemote.Raw
        val parsed = this as? InfraredRemote.Parsed
        return SignalModel.FlipperRemote(
            name = null.orEmpty(), // the name should not affect hash
            type = this.type,
            protocol = parsed?.protocol,
            address = parsed?.address,
            command = parsed?.command,
            frequency = raw?.frequency,
            dutyCycle = raw?.dutyCycle,
            data = raw?.data
        )
    }

    fun encode(remote: InfraredRemote): ByteArray {
        val fRemote = remote.toFlipperRemote()
        return json.encodeToString(fRemote).encodeToByteArray()
    }
}
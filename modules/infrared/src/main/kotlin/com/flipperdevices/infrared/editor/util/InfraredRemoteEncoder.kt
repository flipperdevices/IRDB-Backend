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
            name = this.name,
            type = this.type,
            protocol = parsed?.protocol,
            address = parsed?.address,
            command = parsed?.command,
            frequency = raw?.frequency,
            dutyCycle = raw?.dutyCycle,
            data = raw?.data
        )
    }

    private fun SignalModel.FlipperRemote.toInfraredRemote(): InfraredRemote {
        return runCatching {
            InfraredRemote.Parsed(
                nameInternal = name,
                typeInternal = type,
                protocol = protocol ?: error("Not parsed remote"),
                address = address ?: error("Not parsed remote"),
                command = command ?: error("Not parsed remote")
            )
        }.getOrNull() ?: InfraredRemote.Raw(
            nameInternal = name,
            typeInternal = type,
            frequency = frequency ?: error("Not raw remote"),
            dutyCycle = dutyCycle ?: error("Not raw remote"),
            data = data ?: error("Not raw remote")
        )
    }

    fun encode(remote: InfraredRemote): ByteArray {
        val fRemote = remote.toFlipperRemote()
        return json.encodeToString(fRemote).encodeToByteArray()
    }

    fun decode(byteArray: ByteArray): InfraredRemote {
        val fRemote: SignalModel.FlipperRemote = json.decodeFromString(byteArray.decodeToString())
        return fRemote.toInfraredRemote()
    }
}
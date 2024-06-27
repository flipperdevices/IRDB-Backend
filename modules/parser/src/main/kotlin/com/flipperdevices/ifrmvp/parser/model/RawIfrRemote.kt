package com.flipperdevices.ifrmvp.parser.model

import kotlinx.serialization.Serializable
import java.security.MessageDigest

@Serializable
internal data class RawIfrRemote(
    val name: String,
    val type: String,
    val protocol: String?,
    val address: String?,
    val command: String?,
    val frequency: String?,
    val dutyCycle: String?,
    val data: String?,
) {
    @OptIn(ExperimentalStdlibApi::class)
    val md5: String
        get() {
            val dataByteArray = listOfNotNull(
                type.toByteArray(),
                protocol?.toByteArray(),
                address?.toByteArray(),
                command?.toByteArray(),
                frequency?.toByteArray(),
                dutyCycle?.toByteArray(),
                data?.toByteArray()
            ).flatMap(ByteArray::asList).toByteArray()

            return MessageDigest.getInstance("MD5")
                .digest(dataByteArray)
                .toHexString()
        }
}

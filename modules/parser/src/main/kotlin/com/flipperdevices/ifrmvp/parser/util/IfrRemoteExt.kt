package com.flipperdevices.ifrmvp.parser.util

import com.flipperdevices.ifrmvp.parser.model.RawIfrRemote
import java.security.MessageDigest

internal object IfrRemoteExt {
    @OptIn(ExperimentalStdlibApi::class)
    val RawIfrRemote.md5: String
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

package com.flipperdevices.infrared.editor.encoding

import java.security.MessageDigest

class JvmEncoder(override val algorithm: ByteArrayEncoder.Algorithm) : ByteArrayEncoder {
    private val ByteArrayEncoder.Algorithm.algorithmName: String
        get() = when (algorithm) {
            ByteArrayEncoder.Algorithm.MD5 -> "MD5"
            ByteArrayEncoder.Algorithm.SHA_256 -> "SHA-256"
        }

    @OptIn(ExperimentalStdlibApi::class)
    override fun encode(byteArray: ByteArray): String {
        val algorithmName = algorithm.algorithmName
        return MessageDigest.getInstance(algorithmName)
            .digest(byteArray)
            .toHexString()
    }

}

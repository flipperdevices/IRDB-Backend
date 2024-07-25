package com.flipperdevices.infrared.editor.encoding

internal interface ByteArrayEncoder {
    val algorithm: Algorithm

    fun encode(byteArray: ByteArray): String

    enum class Algorithm { MD5, SHA_256 }
}

package com.flipperdevices.ifrmvp.resources

import java.io.InputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

sealed interface ServerResource {
    fun openStream(): InputStream

    class Image(val path: String) : ServerResource {
        override fun openStream(): InputStream {
            val resource = object {}.javaClass.getResource(path)
            return resource?.openStream() ?: error("Could not load file $path")
        }

        @OptIn(ExperimentalEncodingApi::class)
        fun toBase64(): String {
            return openStream().use {
                PNG_BASE64_HEADER + Base64.encode(it.readAllBytes())
            }
        }

        companion object {
            private const val PNG_BASE64_HEADER = "data:image/png;base64,"
        }
    }
}
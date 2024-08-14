package com.flipperdevices.infrared.editor.encoding

import com.flipperdevices.infrared.editor.model.InfraredRemote

/**
 * To encode [InfraredRemote] we write its objects into list, in specified order.
 *  Then converting all objects inside list into byte arrays so we have lists of byte arrays.
 *  Then we flatten it to have single byte array.
 */
object InfraredRemoteEncoder {
    fun encode(remote: InfraredRemote): ByteArray {
        val bytesList = when (remote) {
            is InfraredRemote.Parsed -> listOf(
                remote.type,
                remote.protocol,
                remote.address,
                remote.command
            )

            is InfraredRemote.Raw -> listOf(
                remote.frequency,
                remote.dutyCycle,
                remote.data
            )
        }
        return bytesList
            .map(String::toByteArray)
            .flatMap(ByteArray::asList)
            .toByteArray()
    }
}
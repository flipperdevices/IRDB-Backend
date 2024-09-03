package com.flipperdevices.infrared.editor.util

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.infrared.editor.model.InfraredRemote
import com.flipperdevices.infrared.editor.viewmodel.FULL_IR_FILETYPE
import com.flipperdevices.infrared.editor.viewmodel.FULL_VERSION_1
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import com.flipperdevices.infrared.editor.viewmodel.KEY_ADDRESS
import com.flipperdevices.infrared.editor.viewmodel.KEY_COMMAND
import com.flipperdevices.infrared.editor.viewmodel.KEY_DATA
import com.flipperdevices.infrared.editor.viewmodel.KEY_DUTY_CYCLE
import com.flipperdevices.infrared.editor.viewmodel.KEY_FREQUENCY
import com.flipperdevices.infrared.editor.viewmodel.KEY_NAME
import com.flipperdevices.infrared.editor.viewmodel.KEY_PROTOCOL
import com.flipperdevices.infrared.editor.viewmodel.KEY_TYPE
import java.io.File

object InfraredMapper {
    fun parseRemotes(raw: String): List<InfraredRemote> {
        return raw
            .let(FlipperFileFormat.Companion::fromFileContent)
            .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)

    }

    fun parseRemotes(file: File): List<InfraredRemote> {
        return file
            .readText()
            .let(::parseRemotes)
    }

    fun toInfraredFormat(remotes: List<InfraredRemote>): String {
        return buildString {
            append(FULL_IR_FILETYPE)
            append("\n")
            append(FULL_VERSION_1)

            remotes.forEachIndexed { index, remote ->
                append("\n")
                append("#")
                append("\n")
                append("$KEY_NAME: ${remote.name}")
                append("\n")
                append("$KEY_TYPE: ${remote.type}")
                append("\n")
                when (remote) {
                    is InfraredRemote.Parsed -> {
                        append("${KEY_PROTOCOL}: ${remote.protocol}")
                        append("\n")
                        append("${KEY_ADDRESS}: ${remote.address}")
                        append("\n")
                        append("${KEY_COMMAND}: ${remote.command}")
                    }

                    is InfraredRemote.Raw -> {
                        append("${KEY_FREQUENCY}: ${remote.frequency}")
                        append("\n")
                        append("${KEY_DUTY_CYCLE}: ${remote.dutyCycle}")
                        append("\n")
                        append("${KEY_DATA}: ${remote.data}")
                    }
                }
            }
            append("\n")
        }
    }

}
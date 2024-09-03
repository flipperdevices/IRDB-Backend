package com.flipperdevices.infrared.editor.viewmodel

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.infrared.editor.model.InfraredRemote

internal const val FULL_IR_FILETYPE = "Filetype: IR signals file"
internal const val FULL_VERSION_1 = "Version: 1"
internal const val KEY_NAME = "name"
internal const val KEY_TYPE = "type"
internal const val KEY_TYPE_RAW = "raw"
internal const val KEY_TYPE_PARSED = "parsed"

internal const val KEY_PROTOCOL = "protocol"
internal const val KEY_ADDRESS = "address"
internal const val KEY_COMMAND = "command"

internal const val KEY_FREQUENCY = "frequency"
internal const val KEY_DUTY_CYCLE = "duty_cycle"
internal const val KEY_DATA = "data"

private val allFields = listOf(
    KEY_NAME,
    KEY_TYPE,
    KEY_PROTOCOL,
    KEY_ADDRESS,
    KEY_COMMAND,
    KEY_FREQUENCY,
    KEY_DUTY_CYCLE,
    KEY_DATA
)

object InfraredKeyParser {
    fun mapParsedKeyToInfraredRemotes(
        fff: FlipperFileFormat
    ): List<InfraredRemote> {
        val blocks = parseKeyContent(fff.orderedDict)
        return parseRemotes(blocks)
    }

    private fun parseKeyContent(
        input: List<Pair<String, String>>
    ): List<List<Pair<String, String>>> {
        val result = mutableListOf<List<Pair<String, String>>>()
        val currentSection = mutableListOf<Pair<String, String>>()

        for (pair in input) {
            if (pair.first.startsWith(KEY_NAME)) {
                if (currentSection.isNotEmpty()) {
                    result.add(currentSection.toList())
                    currentSection.clear()
                }
            }
            if (allFields.contains(pair.first)) {
                currentSection.add(pair)
            }
        }

        if (currentSection.isNotEmpty()) {
            result.add(currentSection.toList())
        }

        return result
    }

    private fun parseRemotes(
        blocks: List<List<Pair<String, String>>>
    ): List<InfraredRemote> {
        return blocks.mapNotNull { block ->
            val blockMap = block.toMap()
            val type = blockMap[KEY_TYPE]

            when (type) {
                KEY_TYPE_RAW -> parseRemoteRaw(blockMap)
                KEY_TYPE_PARSED -> parseRemoteParsed(blockMap)
                else -> null
            }
        }
    }

    private fun parseRemoteRaw(block: Map<String, String>): InfraredRemote.Raw {
        val name = block[KEY_NAME] ?: ""
        val type = block[KEY_TYPE] ?: ""
        val frequency = block[KEY_FREQUENCY] ?: ""
        val dutyCycle = block[KEY_DUTY_CYCLE] ?: ""
        val data = block[KEY_DATA] ?: ""
        return InfraredRemote.Raw(name, type, frequency, dutyCycle, data)
    }

    private fun parseRemoteParsed(block: Map<String, String>): InfraredRemote.Parsed {
        val name = block[KEY_NAME] ?: ""
        val type = block[KEY_TYPE] ?: ""
        val protocol = block[KEY_PROTOCOL] ?: ""
        val address = block[KEY_ADDRESS] ?: ""
        val command = block[KEY_COMMAND] ?: ""
        return InfraredRemote.Parsed(name, type, protocol, address, command)
    }
}

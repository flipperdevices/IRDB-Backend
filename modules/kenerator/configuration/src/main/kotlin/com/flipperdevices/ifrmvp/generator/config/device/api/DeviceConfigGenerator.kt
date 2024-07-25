package com.flipperdevices.ifrmvp.generator.config.device.api

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.model.DeviceConfiguration
import com.flipperdevices.infrared.editor.model.InfraredRemote
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import java.io.File

interface DeviceConfigGenerator {
    fun generate(irFile: File): DeviceConfiguration

    companion object {
        fun parseRemotes(irFile: File): List<InfraredRemote> {
            val content = irFile.readText()
            val fff = FlipperFileFormat.fromFileContent(content)
            val remotes = InfraredKeyParser.mapParsedKeyToInfraredRemotes(fff)
            return remotes
        }
    }
}

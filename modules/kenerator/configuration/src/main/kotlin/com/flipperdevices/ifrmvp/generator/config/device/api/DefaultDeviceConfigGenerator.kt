package com.flipperdevices.ifrmvp.generator.config.device.api

import com.flipperdevices.ifrmvp.backend.model.DeviceConfiguration
import com.flipperdevices.ifrmvp.generator.config.device.api.DeviceKeyNamesProvider.Companion.getKey
import com.flipperdevices.infrared.editor.encoding.InfraredRemoteEncoder.identifier
import java.io.File

class DefaultDeviceConfigGenerator(private val keyNamesProvider: DeviceKeyNamesProvider) : DeviceConfigGenerator {
    override fun generate(irFile: File): DeviceConfiguration {
        val remotes = DeviceConfigGenerator.parseRemotes(irFile)
        val deviceKeyToInstance = remotes.mapNotNull {
            val name = it.name
            val deviceKey = keyNamesProvider.getKey(name) ?: return@mapNotNull null
            deviceKey to it.identifier
        }.associate { pair -> pair }
        return DeviceConfiguration(deviceKeyToInstance)
    }
}

package com.flipperdevices.ifrmvp.generator.config.device.api

import com.flipperdevices.ifrmvp.backend.model.DeviceConfiguration
import com.flipperdevices.ifrmvp.generator.config.device.api.DeviceKeyNamesProvider.Companion.getKey
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.infrared.editor.encoding.ByteArrayEncoder
import com.flipperdevices.infrared.editor.encoding.JvmEncoder
import com.flipperdevices.infrared.editor.util.InfraredRemoteEncoder
import java.io.File

class DefaultDeviceConfigGenerator(private val keyNamesProvider: DeviceKeyNamesProvider) : DeviceConfigGenerator {
    override fun generate(irFile: File): DeviceConfiguration {
        val remotes = DeviceConfigGenerator.parseRemotes(irFile)
        val remoteNameToCount = remotes.groupBy { it.name }
        val deviceKeyToInstance = remotes.mapNotNull {
            val name = it.name
            val deviceKey = keyNamesProvider.getKey(name) ?: return@mapNotNull null
            val byteArray = InfraredRemoteEncoder.encode(it)
            val hash = JvmEncoder(ByteArrayEncoder.Algorithm.SHA_256).encode(byteArray)
            val identifier = when {
                (remoteNameToCount[name]?.size ?: 0) > 1 -> IfrKeyIdentifier.Sha256(name = name, hash = hash)
                else -> IfrKeyIdentifier.Name(name = name)
            }
            deviceKey to identifier
        }.associate { pair -> pair }
        return DeviceConfiguration(deviceKeyToInstance)
    }
}

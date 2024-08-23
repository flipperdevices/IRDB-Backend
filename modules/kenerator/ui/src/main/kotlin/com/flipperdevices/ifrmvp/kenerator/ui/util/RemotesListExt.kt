package com.flipperdevices.ifrmvp.kenerator.ui.util

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import com.flipperdevices.ifrmvp.kenerator.ui.button.ChannelButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.NavigationButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.OkNavigationButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.VolButton
import com.flipperdevices.infrared.editor.model.InfraredRemote

internal object RemotesListExt {
    fun List<InfraredRemote>.findByKey(key: DeviceKey): InfraredRemote? {
        val keyNames = AnyDeviceKeyNamesProvider.getKeyNames(key)
        return firstOrNull { keyNames.contains(it.name.lowercase()) }
    }

    fun List<InfraredRemote>.findNavigationRemote(): NavigationButton? {
        return NavigationButton(
            up = findByKey(DeviceKey.UP) ?: return null,
            down = findByKey(DeviceKey.DOWN) ?: return null,
            left = findByKey(DeviceKey.LEFT) ?: return null,
            right = findByKey(DeviceKey.RIGHT) ?: return null,
        )
    }

    fun List<InfraredRemote>.findOkNavigationRemote(): OkNavigationButton? {
        return OkNavigationButton(
            up = findByKey(DeviceKey.UP) ?: return null,
            down = findByKey(DeviceKey.DOWN) ?: return null,
            left = findByKey(DeviceKey.LEFT) ?: return null,
            right = findByKey(DeviceKey.RIGHT) ?: return null,
            ok = findByKey(DeviceKey.OK) ?: return null,
        )
    }

    fun List<InfraredRemote>.findChannelButton(): ChannelButton? {
        return ChannelButton(
            next = findByKey(DeviceKey.CH_UP) ?: return null,
            prev = findByKey(DeviceKey.CH_DOWN) ?: return null,
        )
    }

    fun List<InfraredRemote>.findVolumeButton(): VolButton? {
        return VolButton(
            add = findByKey(DeviceKey.VOL_UP) ?: return null,
            reduce = findByKey(DeviceKey.VOL_DOWN) ?: return null,
        )
    }
}
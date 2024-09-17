package com.flipperdevices.ifrmvp.kenerator.ui.util

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import com.flipperdevices.ifrmvp.kenerator.ui.button.ChannelButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.NavigationButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.OkNavigationButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.VolButton
import com.flipperdevices.ifrmvp.model.buttondata.Base64ImageButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData.IconType
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.resources.CoreR
import com.flipperdevices.infrared.editor.encoding.InfraredRemoteEncoder.identifier
import com.flipperdevices.infrared.editor.model.InfraredRemote

internal object RemotesListExt {
    fun List<InfraredRemote>.findByKey(key: DeviceKey): InfraredRemote? {
        val keyNames = AnyDeviceKeyNamesProvider.getKeyNames(key)
        return firstOrNull { keyNames.map(String::lowercase).contains(it.name.lowercase()) }
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

    fun List<InfraredRemote>.findButtonsData(): List<ButtonData> {
        val remotes = this
        return DeviceKey.entries.mapNotNull { deviceKey ->
            when (deviceKey) {
                DeviceKey.PWR -> remotes.findByKey(deviceKey)?.let { remote ->
                    PowerButtonData(keyIdentifier = remote.identifier)
                }

                DeviceKey.SHUTTER -> remotes.findByKey(deviceKey)?.let { remote ->
                    ShutterButtonData(keyIdentifier = remote.identifier)
                }


                DeviceKey.FOCUS_MORE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FOCUS_MORE)
                }

                DeviceKey.FOCUS_LESS -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FOCUS_LESS)
                }

                DeviceKey.ZOOM_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.ZOOM_IN)
                }

                DeviceKey.ZOOM_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.ZOOM_OUT)
                }

                DeviceKey.RESET -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.RESET)
                }


                DeviceKey.NEXT -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.NEXT)
                }

                DeviceKey.PREVIOUS -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.PREVIOUS)
                }

                DeviceKey.TV -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.TV)
                }

                DeviceKey.AUX -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.AUX)
                }

                DeviceKey.HOME -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.HOME)
                }

                DeviceKey.BACK -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.BACK)
                }

                DeviceKey.MENU -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.MENU)
                }

                DeviceKey.PLAY -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.PLAY)
                }

                DeviceKey.MUTE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.MUTE)
                }

                DeviceKey.EJECT -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.EJECT)
                }

                DeviceKey.FAN_SPEED -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_SPEED)
                }

                DeviceKey.NEAR -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.NEAR)
                }

                DeviceKey.FAR -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAR)
                }

                DeviceKey.PAUSE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.PAUSE)
                }


                DeviceKey.WIND_SPEED -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.WIND_SPEED)
                }

                DeviceKey.MODE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.MODE)
                }

                DeviceKey.LIGHT -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.LIGHT)
                }

                DeviceKey.FAN_MEDIUM -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_MEDIUM)
                }

                DeviceKey.FAN_HIGH -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_HIGH)
                }

                DeviceKey.FAN_LOW -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_LOW)
                }

                DeviceKey.STOP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.STOP)
                }

                DeviceKey.EXIT -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.EXIT)
                }

                DeviceKey.INFO -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.INFO)
                }

                DeviceKey.TIMER -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.TIMER)
                }

                DeviceKey.OSCILLATE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.OSCILLATE)
                }

                DeviceKey.TIMER_ADD -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.TIMER_ADD)
                }

                DeviceKey.TIMER_REDUCE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.TIMER_REDUCE)
                }

                DeviceKey.FAN_SPEED_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_SPEED_UP)
                }

                DeviceKey.FAN_SPEED_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_SPEED_DOWN)
                }

                DeviceKey.SLEEP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.SLEEP)
                }

                DeviceKey.SHAKE_WIND -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.SHAKE_WIND)
                }

                DeviceKey.SWING -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.SWING)
                }

                DeviceKey.OFF -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAN_OFF)
                }

                DeviceKey.BRIGHTNESS_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.BRIGHT_MORE)
                }

                DeviceKey.BRIGHTNESS_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.BRIGHT_LESS)
                }

                DeviceKey.COLD_WIND -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.COLD_WIND)
                }

                DeviceKey.COOL -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.COOL)
                }

                DeviceKey.WIND_TYPE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.WIND_TYPE)
                }

                DeviceKey.TEMPERATURE_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.TEMPERATURE_UP)
                }

                DeviceKey.TEMPERATURE_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.TEMPERATURE_DOWN)
                }

                DeviceKey.HEAT_ADD -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.HEAT_ADD)
                }

                DeviceKey.HEAT_REDUCE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.HEAT_REDUCE)
                }

                DeviceKey.ENERGY_SAVE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.ENERGY_SAVE)
                }


                DeviceKey.REW -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.REWIND)
                }

                DeviceKey.SET -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.SETTINGS)
                }

                DeviceKey.DELETE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.DELETE)
                }

                DeviceKey.VOD -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.VOD)
                }

                DeviceKey.LIVE_TV -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.LIVE_TV)
                }

                DeviceKey.FAVORITE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAVORITE)
                }

                DeviceKey.INPUT -> remotes.findByKey(deviceKey)?.let { remote ->
                    Base64ImageButtonData(keyIdentifier = remote.identifier, pngBase64 = CoreR.inputImage.toBase64())
                }

                DeviceKey.RECORD -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.RECORD)
                }

                DeviceKey.LEFT,
                DeviceKey.RIGHT,
                DeviceKey.UP,
                DeviceKey.DOWN,
                DeviceKey.CH_DOWN,
                DeviceKey.CH_UP,
                DeviceKey.VOL_DOWN,
                DeviceKey.OK,
                DeviceKey.VOL_UP -> null
            }
        }
    }
}
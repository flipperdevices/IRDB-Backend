package com.flipperdevices.ifrmvp.kenerator.ui.util

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import com.flipperdevices.ifrmvp.kenerator.ui.button.ChannelButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.NavigationButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.OkNavigationButton
import com.flipperdevices.ifrmvp.kenerator.ui.button.VolButton
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData.IconType
import com.flipperdevices.ifrmvp.model.buttondata.PowerButtonData
import com.flipperdevices.ifrmvp.model.buttondata.ShutterButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData
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
                    TextButtonData(keyIdentifier = remote.identifier, text = "F+")
                }

                DeviceKey.FOCUS_LESS -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "F-")
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
                    TextButtonData(keyIdentifier = remote.identifier, text = "TV")
                }

                DeviceKey.AUX -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "AUX")
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
                    TextButtonData(keyIdentifier = remote.identifier, text = "FS")
                }

                DeviceKey.NEAR -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "NEAR")
                }

                DeviceKey.FAR -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "FAR")
                }

                DeviceKey.PAUSE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.PAUSE)
                }


                DeviceKey.WIND_SPEED -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "W+")
                }

                DeviceKey.MODE -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "MODE")
                }

                DeviceKey.LIGHT -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.LIGHT)
                }

                DeviceKey.FAN_MEDIUM -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "F2")
                }

                DeviceKey.FAN_HIGH -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "F3")
                }

                DeviceKey.FAN_LOW -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "F1")
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
                    TextButtonData(keyIdentifier = remote.identifier, text = "T")
                }

                DeviceKey.OSCILLATE -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "OSC")
                }

                DeviceKey.TIMER_ADD -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "TM+")
                }

                DeviceKey.TIMER_REDUCE -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "TM-")
                }

                DeviceKey.FAN_SPEED_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "SP+")
                }

                DeviceKey.FAN_SPEED_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "SP-")
                }

                DeviceKey.SLEEP -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "SLEEP")
                }

                DeviceKey.SHAKE_WIND -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "SHK")
                }

                DeviceKey.SWING -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "SWING")
                }

                DeviceKey.OFF -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "OFF")
                }

                DeviceKey.BRIGHTNESS_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.BRIGHT_MORE)
                }

                DeviceKey.BRIGHTNESS_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.BRIGHT_LESS)
                }

                DeviceKey.COLD_WIND -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "COLD_WIND")
                }

                DeviceKey.COOL -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "COOL")
                }

                DeviceKey.WIND_TYPE -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "WT")
                }

                DeviceKey.TEMPERATURE_UP -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "T+")
                }

                DeviceKey.TEMPERATURE_DOWN -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "T-")
                }

                DeviceKey.HEAT_ADD -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "H+")
                }

                DeviceKey.HEAT_REDUCE -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "H-")
                }

                DeviceKey.ENERGY_SAVE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.ENERGY_SAVE)
                }


                DeviceKey.REW -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "REW")
                }

                DeviceKey.SET -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "SET")
                }

                DeviceKey.DELETE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.DELETE)
                }

                DeviceKey.VOD -> remotes.findByKey(deviceKey)?.let { remote ->
                    TextButtonData(keyIdentifier = remote.identifier, text = "VOD")
                }

                DeviceKey.LIVE_TV -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.LIVE_TV)
                }

                DeviceKey.FAVORITE -> remotes.findByKey(deviceKey)?.let { remote ->
                    IconButtonData(keyIdentifier = remote.identifier, iconId = IconType.FAVORITE)
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
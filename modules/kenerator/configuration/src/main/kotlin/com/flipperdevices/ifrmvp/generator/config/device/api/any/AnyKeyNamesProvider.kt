package com.flipperdevices.ifrmvp.generator.config.device.api.any

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.device.api.DeviceKeyNamesProvider

object AnyKeyNamesProvider : DeviceKeyNamesProvider {
    @Suppress("LongMethod", "CyclomaticComplexMethod")
    override fun getKeyNames(key: DeviceKey): List<String> {
        return when (key) {
            DeviceKey.PWR -> listOf(
                "pwr",
                "power",
                "power_r",
                "on",
            )

            DeviceKey.VOL_DOWN -> listOf(
                "vol-",
                "vol-_r"
            )

            DeviceKey.VOL_UP -> listOf(
                "vol+",
                "vol+_r"
            )

            DeviceKey.CH_UP -> listOf(
                "ch+",
                "ch+_r"
            )

            DeviceKey.CH_DOWN -> listOf(
                "ch-",
                "ch-_r"
            )

            DeviceKey.FOCUS_MORE -> listOf(
                "focus-"
            )

            DeviceKey.FOCUS_LESS -> listOf(
                "focus+"
            )

            DeviceKey.ZOOM_UP -> listOf(
                "zoom+",
                "zoom_up",
                "digital zoom+"
            )

            DeviceKey.ZOOM_DOWN -> listOf(
                "zoom-",
                "zoom_down",
                "digital zoom-"
            )

            DeviceKey.RESET -> listOf(
                "reset"
            )

            DeviceKey.DOWN -> listOf(
                "down",
                "down_r"
            )

            DeviceKey.UP -> listOf(
                "up",
                "up_r"
            )

            DeviceKey.RIGHT -> listOf(
                "right",
                "right_r"
            )

            DeviceKey.LEFT -> listOf(
                "left",
                "left_r"
            )

            DeviceKey.NEXT -> listOf(
                "next",
                "next_r"
            )

            DeviceKey.PREVIOUS -> listOf(
                "previous",
                "previous_r"
            )

            DeviceKey.TV -> listOf(
                "tv",
                "tv_av",
                "tv_r",
                "tvp3",
                "livetv",
                "hd tv"
            )

            DeviceKey.AUX -> listOf(
                "aux",
                "aux_r",
                "input aux",
                "input aux_r"
            )

            DeviceKey.HOME -> listOf(
                "home",
                "home_r"
            )

            DeviceKey.BACK -> listOf(
                "back",
                "back_r"
            )

            DeviceKey.MENU -> listOf(
                "menu",
                "menu_r",
                "pop-up menu",
                "shortcut menu"
            )

            DeviceKey.PLAY -> listOf(
                "play",
                "play_r"
            )

            DeviceKey.MUTE -> listOf(
                "mute",
                "mute_r"
            )

            DeviceKey.FAN_SPEED -> listOf(
                "fanspeed",
                "fan_speed"
            )

            DeviceKey.NEAR -> listOf(
                "near",
                "p_near"
            )

            DeviceKey.FAR -> listOf(
                "far"
            )

            DeviceKey.PAUSE -> listOf(
                "pause"
            )

            DeviceKey.WIND_SPEED -> listOf(
                "wind_speed"
            )

            DeviceKey.MODE -> listOf(
                "mode",
                "mode_r"
            )

            DeviceKey.FAN_SPEED_UP -> listOf(
                "fanspeed+"
            )

            DeviceKey.FAN_SPEED_DOWN -> listOf(
                "fanspeed-"
            )

            DeviceKey.SHAKE_WIND -> listOf(
                "shake_wind"
            )

            DeviceKey.WIND_TYPE -> listOf(
                "wind_type"
            )

            DeviceKey.TEMPERATURE_UP -> listOf(
                "temperature_up"
            )

            DeviceKey.TEMPERATURE_DOWN -> listOf(
                "temperature_down"
            )

            DeviceKey.ENERGY_SAVE -> listOf(
                "energy save",
                "energy save_r"
            )
        }
    }
}

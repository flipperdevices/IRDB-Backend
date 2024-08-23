package com.flipperdevices.ifrmvp.generator.config.device.api.any

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.device.api.DeviceKeyNamesProvider

object AnyDeviceKeyNamesProvider : DeviceKeyNamesProvider {
    @Suppress("LongMethod", "CyclomaticComplexMethod")
    override fun getKeyNames(key: DeviceKey): List<String> {
        // NOT CASE-SENSITIVE!!!
        return when (key) {
            DeviceKey.PWR -> listOf(
                "power",
                "pwr",
                "power",
                "power_r",
                "on",
                "on_off",
                "on/off",
            )

            DeviceKey.VOL_DOWN -> listOf(
                "vol-",
                "vol-_r",
                "voldown",
                "vol_dn"
            )

            DeviceKey.VOL_UP -> listOf(
                "vol+",
                "vol+_r",
                "volup",
                "vol_up"
            )

            DeviceKey.CH_UP -> listOf(
                "ch+",
                "ch+_r",
                "ch_next",
                "chn_up",
                "chann_up",
                "chan up",
                "up",
                "next"
            )

            DeviceKey.CH_DOWN -> listOf(
                "ch-",
                "ch-_r",
                "ch_prev",
                "chn_down",
                "chann_down",
                "chan down",
                "down",
                "prev"
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
                "fan_speed",
                "speed"
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
                "wind_speed",
                "strength"
            )

            DeviceKey.MODE -> listOf(
                "mode",
                "mode_r"
            )

            DeviceKey.FAN_SPEED_UP -> listOf(
                "fanspeed+",
                "fan+",
                "fan_up",
                "speed_up"
            )

            DeviceKey.FAN_SPEED_DOWN -> listOf(
                "fanspeed-",
                "fan-",
                "fan_dn",
                "speed_down"
            )

            DeviceKey.SHAKE_WIND -> listOf(
                "shake_wind"
            )

            DeviceKey.WIND_TYPE -> listOf(
                "wind_type"
            )

            DeviceKey.TEMPERATURE_UP -> listOf(
                "temperature_up",
                "heat+",
                "heat_hi",
                "temp+",
                "heat_up"
            )

            DeviceKey.TEMPERATURE_DOWN -> listOf(
                "temperature_down",
                "heat-",
                "heat_lo",
                "temp-",
                "heat_down"
            )

            DeviceKey.ENERGY_SAVE -> listOf(
                "energy save",
                "energy save_r"
            )

            DeviceKey.SHUTTER -> listOf(
                "shutter",
                "trigger"
            )

            DeviceKey.OK -> listOf(
                "ok",
                "enter",
                "center"
            )
        }
    }
}

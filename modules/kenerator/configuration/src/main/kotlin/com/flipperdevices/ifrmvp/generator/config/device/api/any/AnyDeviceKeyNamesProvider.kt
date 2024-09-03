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
                "pwron",
                "power",
                "power_r",
                "on",
                "on_off",
                "on/off",
                "power_on",
                "power on",
                "powertoggle"
            )

            DeviceKey.VOL_DOWN -> listOf(
                "vol-",
                "vol-_r",
                "voldown",
                "voldwn",
                "vol_dn",
                "vold",
                "volume_down",
                "volume down",
                "vol_donw",
                "vol_dwn",
                "amp vol-",
                "cent_volume_down",
                "ch_vol-",
                "master volume-",
                "mastervol-",
            )

            DeviceKey.VOL_UP -> listOf(
                "vol+",
                "vol+_r",
                "volup",
                "vol_up",
                "volu",
                "volume_up",
                "volume up",
                "amp vol-",
                "cent_volume_up",
                "ch_vol+",
                "master volume+",
                "mastervol-",
            )

            DeviceKey.CH_UP -> listOf(
                "ch+",
                "ch+_r",
                "ch_next",
                "chn_up",
                "chann_up",
                "chan up",
                "up",
                "up/ch+",
                "tv_ch+",
            )

            DeviceKey.CH_DOWN -> listOf(
                "ch-",
                "ch-_r",
                "ch_prev",
                "chn_down",
                "chann_down",
                "chan down",
                "down",
                "tv_ch-",
            )

            DeviceKey.FOCUS_MORE -> listOf(
                "focus-",
                "focus_dwn",
                "focusing-"
            )

            DeviceKey.FOCUS_LESS -> listOf(
                "focus+",
                "focus_up",
                "focusing+"
            )

            DeviceKey.ZOOM_UP -> listOf(
                "zoom+",
                "ezoom+",
                "zoom_up",
                "digital zoom+",
                "zoom_in",
                "ezoom_in",
                "zoomin"
            )

            DeviceKey.ZOOM_DOWN -> listOf(
                "zoom-",
                "ezoom-",
                "zoom_down",
                "digital zoom-",
                "zoom out",
                "zoom_out",
                "ezoom_out"
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

            DeviceKey.REW -> listOf(
                "rew",
            )

            DeviceKey.HOME -> listOf(
                "home",
                "home_r",
            )

            DeviceKey.BACK -> listOf(
                "back",
                "back_r",
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

            DeviceKey.EJECT -> listOf(
                "eject",
            )

            DeviceKey.STOP -> listOf(
                "stop",
                "stop_r"
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

            DeviceKey.RECORD -> listOf(
                "record",
            )

            DeviceKey.MODE -> listOf(
                "mode",
                "mode_r"
            )

            DeviceKey.FAN_SPEED_UP -> listOf(
                "fanspeed+",
                "fan+",
                "fan +",
                "fan_up",
                "speed_up"
            )

            DeviceKey.FAN_SPEED_DOWN -> listOf(
                "fanspeed-",
                "fan-",
                "fan -",
                "fan_dn",
                "speed_down"
            )

            DeviceKey.SLEEP -> listOf(
                "sleep"
            )

            DeviceKey.FAN_MEDIUM -> listOf(
                "medium"
            )

            DeviceKey.FAN_HIGH -> listOf(
                "high"
            )

            DeviceKey.FAN_LOW -> listOf(
                "low"
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
                "trigger",
                "photo",
                "picture"
            )

            DeviceKey.OK -> listOf(
                "ok",
                "enter",
                "center",
                "apply",
                "sure"
            )

            DeviceKey.TIMER -> listOf(
                "timer"
            )

            DeviceKey.COLD_WIND -> listOf(
                "cold wind",
            )

            DeviceKey.COOL -> listOf(
                "cool",
            )

            DeviceKey.TIMER_ADD -> listOf(
                "timer+"
            )

            DeviceKey.OSCILLATE -> listOf(
                "oscillate",
                "osc",
                "oscillation",
                "oscilate",
                "oscil"
            )

            DeviceKey.TIMER_REDUCE -> listOf(
                "timer-"
            )

            DeviceKey.SWING -> listOf(
                "swing"
            )

            DeviceKey.OFF -> listOf(
                "off",
                "power_off",
                "power off",
                "poweroff",
                "pwroff"
            )

            DeviceKey.HEAT_ADD -> listOf(
                "heat_hi",
                "heat+"
            )

            DeviceKey.HEAT_REDUCE -> listOf(
                "heat_lo",
                "heat-"
            )

            DeviceKey.EXIT -> listOf(
                "exit",
            )

            DeviceKey.INFO -> listOf(
                "info",
            )

            DeviceKey.BRIGHTNESS_UP -> listOf(
                "+brightness",
                "bright_up",
                "brightness up",
                "brightness_up"
            )

            DeviceKey.BRIGHTNESS_DOWN -> listOf(
                "-brightness",
                "brightness_down",
                "brightness_dn"

            )

            DeviceKey.SET -> listOf(
                "set"
            )

            DeviceKey.DELETE -> listOf(
                "del",
                "delete"
            )

            DeviceKey.VOD -> listOf(
                "vod"
            )

            DeviceKey.LIVE_TV -> listOf(
                "live tv",
                "live tv_r",
                "livetv",
                "tv",
                "letv"
            )

            DeviceKey.FAVORITE -> listOf(
                "favorites",
                "favorite"
            )

            DeviceKey.LIGHT -> listOf(
                "light"
            )
        }
    }
}

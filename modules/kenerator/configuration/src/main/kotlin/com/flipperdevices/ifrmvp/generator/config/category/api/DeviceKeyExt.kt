package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey

object DeviceKeyExt {
    @Suppress("LongMethod", "CyclomaticComplexMethod")
    fun DeviceKey.getAllowedCategories(): List<CategoryType> {
        return when (this) {
            DeviceKey.OFF,
            DeviceKey.PWR -> listOf(
                CategoryType.TVS,
                CategoryType.AIR_PURIFIERS,
                CategoryType.BOX,
                CategoryType.FAN,
                CategoryType.PROJECTOR,
                CategoryType.A_V_RECEIVER,
            )

            DeviceKey.VOL_DOWN,
            DeviceKey.VOL_UP -> listOf(
                CategoryType.TVS,
                CategoryType.BOX,
                CategoryType.DVD,
                CategoryType.PROJECTOR,
                CategoryType.A_V_RECEIVER,
            )

            DeviceKey.RECORD -> listOf(
                CategoryType.TVS,
            )

            DeviceKey.CH_DOWN,
            DeviceKey.CH_UP -> listOf(
                CategoryType.TVS,
                CategoryType.BOX
            )

            DeviceKey.FOCUS_LESS,
            DeviceKey.FOCUS_MORE -> listOf(
                CategoryType.PROJECTOR,
                CategoryType.BOX,
                CategoryType.DVD
            )

            DeviceKey.ZOOM_DOWN,
            DeviceKey.ZOOM_UP -> listOf(
                CategoryType.PROJECTOR,
                CategoryType.TVS,
                CategoryType.DVD,
                CategoryType.CAMERA,
                CategoryType.A_V_RECEIVER,
            )

            DeviceKey.RESET -> listOf(
                CategoryType.PROJECTOR,
                CategoryType.CAMERA,
                CategoryType.TVS,
                CategoryType.DVD,
                CategoryType.A_V_RECEIVER,
            )

            DeviceKey.OK,
            DeviceKey.LEFT,
            DeviceKey.RIGHT,
            DeviceKey.UP,
            DeviceKey.DOWN -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.PROJECTOR,
                CategoryType.AIR_PURIFIERS,
                CategoryType.FAN,
            )


            DeviceKey.PREVIOUS,
            DeviceKey.NEXT -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.BOX
            )

            DeviceKey.EJECT -> listOf(
                CategoryType.DVD,
            )

            DeviceKey.REW,
            DeviceKey.AUX,
            DeviceKey.TV -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
            )

            DeviceKey.INFO,
            DeviceKey.EXIT,
            DeviceKey.MENU,
            DeviceKey.HOME,
            DeviceKey.BACK -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.PROJECTOR
            )

            DeviceKey.PAUSE,
            DeviceKey.PLAY,
            DeviceKey.STOP -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.DVD,
                CategoryType.BOX
            )

            DeviceKey.MUTE -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.AIR_PURIFIERS,
                CategoryType.DVD,
                CategoryType.FAN,
                CategoryType.PROJECTOR,
                CategoryType.BOX
            )


            DeviceKey.MODE -> listOf(
                CategoryType.FAN,
                CategoryType.AIR_PURIFIERS
            )

            DeviceKey.FAN_MEDIUM,
            DeviceKey.FAN_HIGH,
            DeviceKey.FAN_LOW,
            DeviceKey.LIGHT -> listOf(
                CategoryType.FAN,
            )

            DeviceKey.FAN_SPEED,
            DeviceKey.SWING,
            DeviceKey.COLD_WIND,
            DeviceKey.COOL,
            DeviceKey.WIND_TYPE,
            DeviceKey.SHAKE_WIND,
            DeviceKey.WIND_SPEED,
            DeviceKey.FAN_SPEED_DOWN,
            DeviceKey.SLEEP,
            DeviceKey.FAN_SPEED_UP -> listOf(
                CategoryType.FAN,
                CategoryType.AIR_PURIFIERS
            )

            DeviceKey.TIMER_REDUCE,
            DeviceKey.OSCILLATE,
            DeviceKey.TIMER,
            DeviceKey.TIMER_ADD,
            DeviceKey.TEMPERATURE_DOWN,
            DeviceKey.TEMPERATURE_UP -> listOf(
                CategoryType.FAN,
                CategoryType.AIR_PURIFIERS
            )

            DeviceKey.ENERGY_SAVE -> listOf(
                CategoryType.FAN
            )

            DeviceKey.FAR,
            DeviceKey.NEAR,
            DeviceKey.SHUTTER -> listOf(
                CategoryType.CAMERA,
                CategoryType.PROJECTOR
            )

            DeviceKey.HEAT_REDUCE,
            DeviceKey.HEAT_ADD -> listOf(
                CategoryType.AIR_PURIFIERS,
            )

            DeviceKey.BRIGHTNESS_DOWN,
            DeviceKey.BRIGHTNESS_UP -> listOf(
                CategoryType.PROJECTOR,
                CategoryType.TVS,
                CategoryType.DVD,
                CategoryType.A_V_RECEIVER,
            )

            DeviceKey.SET,
            DeviceKey.DELETE,
            DeviceKey.VOD,
            DeviceKey.LIVE_TV,
            DeviceKey.FAVORITE -> listOf(
                CategoryType.BOX
            )
        }
    }
}

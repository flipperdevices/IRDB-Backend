package com.flipperdevices.ifrmvp.generator.config.category.api

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryType

object DeviceKeyExt {
    @Suppress("LongMethod", "CyclomaticComplexMethod")
    fun DeviceKey.getAllowedCategories(): List<CategoryType> {
        return when (this) {
            DeviceKey.PWR -> listOf(
                CategoryType.TVS,
                CategoryType.AIR_PURIFIERS,
                CategoryType.BOX,
                CategoryType.FAN,
                CategoryType.PROJECTOR
            )

            DeviceKey.VOL_UP -> listOf(
                CategoryType.TVS,
                CategoryType.BOX,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.VOL_DOWN -> listOf(
                CategoryType.TVS,
                CategoryType.BOX,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.CH_UP -> listOf(
                CategoryType.TVS,
                CategoryType.BOX
            )

            DeviceKey.CH_DOWN -> listOf(
                CategoryType.TVS,
                CategoryType.BOX
            )

            DeviceKey.FOCUS_MORE -> listOf(
                CategoryType.PROJECTOR
            )

            DeviceKey.FOCUS_LESS -> listOf(
                CategoryType.PROJECTOR
            )

            DeviceKey.ZOOM_UP -> listOf(
                CategoryType.PROJECTOR
            )

            DeviceKey.ZOOM_DOWN -> listOf(
                CategoryType.PROJECTOR
            )

            DeviceKey.RESET -> listOf(
                CategoryType.PROJECTOR
            )

            DeviceKey.DOWN -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.UP -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.RIGHT -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.LEFT -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.NEXT -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.CAMERA,
                CategoryType.DVD
            )

            DeviceKey.PREVIOUS -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.CAMERA,
                CategoryType.DVD
            )

            DeviceKey.TV -> listOf(
                CategoryType.A_V_RECEIVER
            )

            DeviceKey.AUX -> listOf(
                CategoryType.A_V_RECEIVER
            )

            DeviceKey.HOME -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.DVD
            )

            DeviceKey.BACK -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.BOX,
                CategoryType.CAMERA,
                CategoryType.PROJECTOR
            )

            DeviceKey.MENU -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.CAMERA,
                CategoryType.DVD,
                CategoryType.PROJECTOR
            )

            DeviceKey.PLAY -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.DVD
            )

            DeviceKey.MUTE -> listOf(
                CategoryType.A_V_RECEIVER,
                CategoryType.AIR_PURIFIERS,
                CategoryType.DVD,
                CategoryType.FAN,
                CategoryType.PROJECTOR
            )

            DeviceKey.FAN_SPEED -> listOf(
                CategoryType.AIR_PURIFIERS
            )

            DeviceKey.NEAR -> listOf(
                CategoryType.CAMERA
            )

            DeviceKey.FAR -> listOf(
                CategoryType.CAMERA
            )

            DeviceKey.PAUSE -> listOf(
                CategoryType.DVD
            )

            DeviceKey.WIND_SPEED -> listOf(
                CategoryType.FAN
            )

            DeviceKey.MODE -> listOf(
                CategoryType.FAN
            )

            DeviceKey.FAN_SPEED_UP -> listOf(
                CategoryType.FAN
            )

            DeviceKey.FAN_SPEED_DOWN -> listOf(
                CategoryType.FAN
            )

            DeviceKey.SHAKE_WIND -> listOf(
                CategoryType.FAN
            )

            DeviceKey.WIND_TYPE -> listOf(
                CategoryType.FAN
            )

            DeviceKey.TEMPERATURE_UP -> listOf(
                CategoryType.FAN
            )

            DeviceKey.TEMPERATURE_DOWN -> listOf(
                CategoryType.FAN
            )

            DeviceKey.ENERGY_SAVE -> listOf(
                CategoryType.FAN
            )
        }
    }
}

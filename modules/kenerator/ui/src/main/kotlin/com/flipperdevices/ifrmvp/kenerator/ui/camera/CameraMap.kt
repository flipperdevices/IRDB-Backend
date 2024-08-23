package com.flipperdevices.ifrmvp.kenerator.ui.camera

import com.flipperdevices.ifrmvp.kenerator.ui.core.BrandMap
import com.flipperdevices.ifrmvp.model.IfrButton.Position
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData.ButtonType

internal class CameraMap : BrandMap by BrandMap.Default(
    buildMap = {
        ButtonType.entries.associateWith {
            when (it) {
                ButtonType.POWER -> listOf(
                    Position(x = 2, y = 0)
                )

                ButtonType.UNKNOWN,
                ButtonType.TEXT,
                ButtonType.ICON,
                ButtonType.BASE64_IMAGE -> buildList {
                    Position(x = 0, y = 0).run(::add)
                    Position(x = 1, y = 0).run(::add)
                    Position(x = 3, y = 0).run(::add)
                    Position(x = 4, y = 0).run(::add)
                    (0..4).map { x -> Position(x = x, y = 1) }.run(::addAll)
                    (0..4).map { x -> Position(x = x, y = 10) }.run(::addAll)
                }


                ButtonType.SHUTTER -> listOf(
                    Position(x = 1, y = 3, containerHeight = 3, containerWidth = 3)
                )

                ButtonType.OK_NAVIGATION,
                ButtonType.NAVIGATION -> listOf(
                    Position(x = 1, y = 7, containerHeight = 3, containerWidth = 3)
                )


                ButtonType.VOLUME,
                ButtonType.CHANNEL -> emptyList()
            }
        }
    }
)
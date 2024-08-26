package com.flipperdevices.ifrmvp.kenerator.ui.category.fan

import com.flipperdevices.ifrmvp.kenerator.ui.core.BrandMap
import com.flipperdevices.ifrmvp.model.IfrButton.Position
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData.ButtonType

internal class FanMap : BrandMap by BrandMap.Default(
    buildMap = {
        val oneTypeButtonLocations = buildList {
            Position(x = 0, y = 0).run(::add)
            Position(x = 4, y = 0).run(::add)
            (0..4).map { x -> Position(x = x, y = 1) }.run(::addAll)
            (0..4).map { x -> Position(x = x, y = 4) }.run(::addAll)
            (0..4).map { x -> Position(x = x, y = 5) }.run(::addAll)
        }
        ButtonType.entries.associateWith {
            when (it) {
                ButtonType.POWER -> oneTypeButtonLocations

                ButtonType.UNKNOWN,
                ButtonType.TEXT,
                ButtonType.ICON,
                ButtonType.BASE64_IMAGE -> oneTypeButtonLocations


                ButtonType.SHUTTER,
                ButtonType.VOLUME,
                ButtonType.CHANNEL,
                ButtonType.OK_NAVIGATION,
                ButtonType.NAVIGATION -> emptyList()
            }
        }
    }
)
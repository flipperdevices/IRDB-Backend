package com.flipperdevices.ifrmvp.kenerator.ui.category.tv

import com.flipperdevices.ifrmvp.kenerator.ui.core.BrandMap
import com.flipperdevices.ifrmvp.model.IfrButton.Position
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData.ButtonType

internal class TvMap : BrandMap by BrandMap.Default(
    getMap = {
        val navigationPositions = mutableListOf(
            Position(x = 1, y = 3, containerHeight = 3, containerWidth = 3)
        )
        val defaultPositions = buildList {
            Position(x = 2, y = 0).run(::add)
            Position(x = 4, y = 0).run(::add)
            (0..4).map { x -> Position(x = x, y = 1) }.run(::addAll)
            (0..4).map { x -> Position(x = x, y = 10) }.run(::addAll)
        }.toMutableList()
        ButtonType.entries.associateWith {
            when (it) {
                ButtonType.POWER -> mutableListOf(
                    Position(x = 0, y = 0)
                )

                ButtonType.UNKNOWN,
                ButtonType.TEXT,
                ButtonType.ICON,
                ButtonType.BASE64_IMAGE -> defaultPositions

                ButtonType.CHANNEL -> mutableListOf(
                    Position(x = 0, y = 7, containerHeight = 3, containerWidth = 1)
                )

                ButtonType.OK_NAVIGATION,
                ButtonType.NAVIGATION -> navigationPositions

                ButtonType.VOLUME -> mutableListOf(
                    Position(x = 4, y = 7, containerHeight = 3, containerWidth = 1)
                )

                ButtonType.SHUTTER -> mutableListOf()
            }
        }.toMutableMap()
    }
)
package com.flipperdevices.ifrmvp.kenerator.ui.core

import com.flipperdevices.ifrmvp.model.IfrButton
import com.flipperdevices.ifrmvp.model.buttondata.ButtonData

/**
 * By given [BrandMap.Default.buildMap] map of locations by [ButtonData.ButtonType]
 * [BrandMap] places available [ButtonData] in specified location
 *
 * Thus, we will have minimum amount of empty spaces for specific button types
 *
 * This class should be implemented using delegated and switch every [ButtonData.ButtonType]
 */
interface BrandMap {
    fun transform(buttonsData: List<ButtonData>): List<IfrButton>

    class Default(private val buildMap: () -> Map<ButtonData.ButtonType, List<IfrButton.Position>>) : BrandMap {
        override fun transform(buttonsData: List<ButtonData>): List<IfrButton> {
            val mutableMap = buildMap.invoke().mapValues { it.value.toMutableList() }.toMutableMap()
            return buttonsData.mapNotNull { buttonData ->
                val position = mutableMap[buttonData.type]
                val currentPosition = position?.removeFirstOrNull()
                if (currentPosition == null) {
                    println("Could not find position for ${buttonData::class}")
                    return@mapNotNull null
                }
                IfrButton(
                    data = buttonData,
                    position = currentPosition
                )
            }
        }
    }
}
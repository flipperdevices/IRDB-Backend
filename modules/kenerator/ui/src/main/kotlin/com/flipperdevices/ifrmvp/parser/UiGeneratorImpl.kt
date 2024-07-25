package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.model.IfrButton
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.ifrmvp.model.PageLayout
import com.flipperdevices.ifrmvp.model.PagesLayout
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser

class UiGeneratorImpl : UiGenerator {
    override fun generate(remoteContent: String): PagesLayout {
        val signals = remoteContent
            .let(FlipperFileFormat.Companion::fromFileContent)
            .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
        val windowSize = MAX_ROWS * MAX_COLUMNS
        val chunks = when {
            signals.size > windowSize -> signals.windowed(windowSize) { it }
            else -> listOf(signals)
        }
        return PagesLayout(
            pages = chunks.map { signals ->
                var x = -1
                PageLayout(
                    buttons = signals.map { signal ->
                        x += 1
                        val keyIdentifier = IfrKeyIdentifier.Name(signal.name)
                        IfrButton(
                            data = when {
                                signal.name.contains("pwr", true) ||
                                    signal.name.contains("power", true) ||
                                    signal.name.contains("on", true) -> {
                                    IconButtonData(
                                        keyIdentifier = keyIdentifier,
                                        iconId = IconButtonData.IconType.POWER
                                    )
                                }

                                else -> TextButtonData(
                                    keyIdentifier = keyIdentifier,
                                    text = signal.name
                                )
                            },
                            position = IfrButton.Position(
                                y = (x / MAX_COLUMNS) % MAX_ROWS,
                                x = x % MAX_COLUMNS
                            )
                        )
                    }
                )
            }
        )
    }

    companion object {
        private const val MAX_COLUMNS = 5
        private const val MAX_ROWS = 11
    }
}

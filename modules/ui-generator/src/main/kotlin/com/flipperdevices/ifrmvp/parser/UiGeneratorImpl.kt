package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.model.IfrButton
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.ifrmvp.model.PageLayout
import com.flipperdevices.ifrmvp.model.PagesLayout
import com.flipperdevices.ifrmvp.model.buttondata.TextButtonData
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser

class UiGeneratorImpl : UiGenerator {
    override fun generate(remoteContent: String): PagesLayout {
        val signals = remoteContent
            .let(FlipperFileFormat.Companion::fromFileContent)
            .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
        val chunks = signals.chunked(MAX_ROWS * MAX_COLUMNS) { it }
        return PagesLayout(
            pages = chunks.map { signals ->
                var x = -1
                PageLayout(
                    buttons = signals.map { signal ->
                        x += 1
                        IfrButton(
                            data = TextButtonData(
                                keyIdentifier = IfrKeyIdentifier.Name(signal.name),
                                text = signal.name
                            ),
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

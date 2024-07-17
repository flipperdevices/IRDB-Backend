package com.flipperdevices.ifrmvp.model.serialization

import com.flipperdevices.ifrmvp.model.IfrButton
import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import com.flipperdevices.ifrmvp.model.buttondata.IconButtonData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ButtonDataSerializerTest {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private inline fun <reified T> assertSerializable(value: T) {
        val string = json.encodeToString(value)
        val decodedValue = json.decodeFromString<T>(string)
        assertEquals(value, decodedValue)
    }

    @Test
    fun test() {
        IfrButton(
            data = IconButtonData(
                keyIdentifier = IfrKeyIdentifier.Name("Hello"),
                iconId = IconButtonData.IconType.POWER
            ),
            position = IfrButton.Position(0, 0)
        ).run(::assertSerializable)
    }
}
package com.flipperdevices.ifrmvp.model.buttondata

import com.flipperdevices.ifrmvp.model.IfrKeyIdentifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShutterButtonData(
    @SerialName("key_id")
    override val keyIdentifier: IfrKeyIdentifier = IfrKeyIdentifier.Empty,
) : SingleKeyButtonData {
    override val type: ButtonData.ButtonType = ButtonData.ButtonType.SHUTTER
}

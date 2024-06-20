package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class SignalResponseModel(
    val signalModel: SignalModel? = null,
    val hasNext: Boolean,
    val buttonInfo: ButtonInfo
) {
    @Serializable
    class ButtonInfo(
        val backgroundColor: Long,
        val tintColor: Long,
        val iconUrl: String? = null,
        val iconType: IconType? = null,
        val text: String? = null
    ) {
        @Serializable
        enum class IconType {
            POWER, SOUND_UP, SOUND_DOWN
        }
    }
}

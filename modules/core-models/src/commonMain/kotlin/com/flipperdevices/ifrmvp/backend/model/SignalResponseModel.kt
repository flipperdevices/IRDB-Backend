package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class SignalResponseModel(
    val signalModel: SignalModel? = null,
    val hasNext: Boolean,
    val buttonInfo: SignalButtonInfo
)

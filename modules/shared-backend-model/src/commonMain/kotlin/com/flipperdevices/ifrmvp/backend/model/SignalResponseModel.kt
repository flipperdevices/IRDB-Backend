package com.flipperdevices.ifrmvp.backend.model

import kotlinx.serialization.Serializable

@Serializable
class SignalResponseModel(
    val signalOrderModel: SignalOrderModel? = null,
    val ifrFileModel: IfrFileModel? = null
)

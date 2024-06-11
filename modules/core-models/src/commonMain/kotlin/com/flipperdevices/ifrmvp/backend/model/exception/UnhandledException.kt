package com.flipperdevices.ifrmvp.backend.model.exception

import kotlinx.serialization.Serializable

@Serializable
data object UnhandledException : ServerException()

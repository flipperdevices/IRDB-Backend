package com.flipperdevices.ifrmvp.backend.model.exception

import kotlinx.serialization.Serializable

@Serializable
sealed class ServerException : Throwable()

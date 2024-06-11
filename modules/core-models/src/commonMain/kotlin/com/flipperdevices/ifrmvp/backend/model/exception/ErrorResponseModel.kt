package com.flipperdevices.ifrmvp.backend.model.exception

import kotlinx.serialization.Serializable

@Serializable
class ErrorResponseModel(val serverException: ServerException)

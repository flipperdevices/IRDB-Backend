package com.flipperdevices.ifrmvp.backend.model.exception

import kotlinx.serialization.Serializable

@Serializable
sealed interface ErrorResponseModel {
    @Serializable
    data object Unhandled : ErrorResponseModel

    @Serializable
    data object CategoryNotFound : ErrorResponseModel

    @Serializable
    data object BrandNotFound : ErrorResponseModel

    @Serializable
    data object NoSignal : ErrorResponseModel
}

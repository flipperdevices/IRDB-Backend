package com.flipperdevices.ifrmvp.backend.plugins

import com.flipperdevices.ifrmvp.backend.buildkonfig.BuildKonfig
import com.flipperdevices.ifrmvp.backend.db.signal.exception.TableDaoException
import com.flipperdevices.ifrmvp.backend.model.ErrorResponseModel
import com.flipperdevices.ifrmvp.backend.model.ErrorType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureStatusPages() {
    val logger = KtorSimpleLogger(BuildKonfig.GROUP)
    install(StatusPages) {
        exception<TableDaoException> { call, e ->
            val errorType = when (e) {
                is TableDaoException.BrandNotFound -> ErrorType.BRAND_NOT_FOUND
                is TableDaoException.CategoryMeta -> ErrorType.CATEGORY_META_NOT_FOUND
                is TableDaoException.CategoryNotFound -> ErrorType.CATEGORY_NOT_FOUND
                is TableDaoException.IrFileNotFound -> ErrorType.IR_FILE_NOT_FOUND
            }
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponseModel(errorType),
            )
        }
        unhandled { call ->
            logger.error("Unhandled exception: ${call.response}; ${call.request}")
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponseModel(ErrorType.UNHANDLED),
            )
        }
    }
}

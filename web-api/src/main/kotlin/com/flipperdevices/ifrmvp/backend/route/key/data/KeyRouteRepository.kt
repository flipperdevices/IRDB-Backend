package com.flipperdevices.ifrmvp.backend.route.key.data

import java.io.File

interface KeyRouteRepository {
    suspend fun getIfrFile(ifrFileId: Long): File
}

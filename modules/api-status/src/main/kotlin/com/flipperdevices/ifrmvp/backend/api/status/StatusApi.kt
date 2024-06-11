package com.flipperdevices.ifrmvp.backend.api.status

interface StatusApi {
    suspend fun insertAndGetId(): Long
}

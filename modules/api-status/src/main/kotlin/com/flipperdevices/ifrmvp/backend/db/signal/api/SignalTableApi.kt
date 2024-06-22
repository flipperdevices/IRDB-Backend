package com.flipperdevices.ifrmvp.backend.db.signal.api

import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType

interface SignalTableApi {
    suspend fun addCategory(
        displayName: String,
        deviceType: DeviceCategoryType
    ): Long

    suspend fun addBrand(
        categoryId: Long,
        displayName: String
    ): Long

    suspend fun addIrFile(
        fileName: String,
        categoryId: Long,
        brandId: Long
    ): Long

    suspend fun addUiPreset(
        categoryId: Long,
        brandId: Long,
        irFileId: Long,
        fileName: String
    )

    suspend fun addSignal(
        categoryId: Long,
        brandId: Long,
        irFileId: Long,
        name: String,
        type: String,
        protocol: String?,
        address: String?,
        command: String?,
        frequency: String?,
        dutyCycle: String?,
        data: String?
    )
}

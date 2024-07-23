package com.flipperdevices.ifrmvp.parser.api

import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.parser.model.RawIfrRemote

internal interface SignalTableApi {
    suspend fun addCategory(
        categoryFolderName: String,
    ): Long

    suspend fun addBrand(
        categoryId: Long,
        displayName: String
    ): Long

    suspend fun addIrFile(
        fileName: String,
        categoryId: Long,
        brandId: Long,
        folderName: String
    ): Long

    suspend fun addUiPreset(
        categoryId: Long,
        brandId: Long,
        irFileId: Long,
        fileName: String
    )

    suspend fun addSignal(
        brandId: Long,
        remote: RawIfrRemote
    ): Long

    suspend fun addCategoryMeta(
        categoryId: Long,
        meta: CategoryMeta
    )

    suspend fun linkFileAndSignal(
        infraredFileId: Long,
        signalId: Long
    )
}

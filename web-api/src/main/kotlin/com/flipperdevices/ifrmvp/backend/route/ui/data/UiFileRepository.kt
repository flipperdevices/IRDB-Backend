package com.flipperdevices.ifrmvp.backend.route.ui.data

import com.flipperdevices.ifrmvp.backend.model.UiPresetModel

interface UiFileRepository {
    suspend fun getUiFileModelOrNull(ifrFileId: Long): UiPresetModel?
    suspend fun getUiFileContent(uiFileModel: UiPresetModel): String
}

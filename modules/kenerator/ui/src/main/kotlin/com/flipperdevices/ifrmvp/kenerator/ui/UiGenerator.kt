package com.flipperdevices.ifrmvp.kenerator.ui

import com.flipperdevices.ifrmvp.model.PagesLayout

interface UiGenerator {
    suspend fun generate(irFileId: Long): PagesLayout
}

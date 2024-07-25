package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.model.PagesLayout

interface UiGenerator {
    fun generate(remoteContent: String): PagesLayout
}

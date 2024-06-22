package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object UiPresetTable : LongIdTable("UI_PRESET") {
    val categoryRef = reference("category_id", CategoryTable)
    val brandRef = reference("brand_id", BrandTable)
    val ifrFileRef = reference("ifr_file_id", IfrFileTable)
    val fileName = text("file_name")
}

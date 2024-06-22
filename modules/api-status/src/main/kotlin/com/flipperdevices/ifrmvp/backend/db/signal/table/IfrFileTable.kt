package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object IfrFileTable : LongIdTable("IFR_FILE") {
    val categoryRef = reference("category_id", CategoryTable)
    val brandRef = reference("brand_id", BrandTable)
    val fileName = text("file_name")
}

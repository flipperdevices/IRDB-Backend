package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object IfrFileTable : LongIdTable("IFR_FILE") {
    val categoryId = reference("category_id", CategoryTable)
    val brandId = reference("brand_id", BrandTable)
    val fileName = text("file_name")
}

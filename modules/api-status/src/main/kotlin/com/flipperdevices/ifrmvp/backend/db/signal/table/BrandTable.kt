package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object BrandTable : LongIdTable("BRAND") {
    val category = reference("category_id", CategoryTable)
    val displayName = text("display_name")
}

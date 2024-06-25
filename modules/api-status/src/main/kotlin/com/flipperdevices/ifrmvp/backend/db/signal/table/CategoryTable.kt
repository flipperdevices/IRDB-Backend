package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object CategoryTable : LongIdTable("CATEGORY") {
    val categoryFolderName = text("folder_name")
}

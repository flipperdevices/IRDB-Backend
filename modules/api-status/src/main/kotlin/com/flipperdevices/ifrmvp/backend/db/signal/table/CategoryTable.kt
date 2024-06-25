package com.flipperdevices.ifrmvp.backend.db.signal.table

import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType
import org.jetbrains.exposed.dao.id.LongIdTable

object CategoryTable : LongIdTable("CATEGORY") {
    val categoryFolderName = text("folder_name")
}

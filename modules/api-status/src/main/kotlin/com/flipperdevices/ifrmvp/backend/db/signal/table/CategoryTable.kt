package com.flipperdevices.ifrmvp.backend.db.signal.table

import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType
import org.jetbrains.exposed.dao.id.LongIdTable

object CategoryTable : LongIdTable("CATEGORY") {
    val displayName = text("display_name")
    val deviceType = enumeration<DeviceCategoryType>("device_type")
}

package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Every category of IRDB
 */
object CategoryTable : LongIdTable("CATEGORY") {
    /**
     * The name of category folder inside IRDB
     */
    val folderName = text("folder_name").uniqueIndex()
}

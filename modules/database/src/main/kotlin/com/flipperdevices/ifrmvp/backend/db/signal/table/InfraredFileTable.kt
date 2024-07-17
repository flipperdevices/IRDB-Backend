package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Every .ir file from IRDB
 */
object InfraredFileTable : LongIdTable("INFRARED_FILE") {
    /**
     * Reference to current brand identifier
     */
    val brandId = reference("brand_id", BrandTable)

    /**
     * The name of .ir file inside /CATEGORY/BRAND/MODEL/XXX.ir
     */
    val fileName = text("file_name")

    /**
     * The name of .IR file folder
     */
    val folderName = text("folder_name")
}

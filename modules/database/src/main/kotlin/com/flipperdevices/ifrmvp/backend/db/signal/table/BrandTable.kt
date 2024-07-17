package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Brands folder converted to tables
 *
 * Every brand of every category with parent category [BrandTable.categoryId]
 */
object BrandTable : LongIdTable("BRAND") {
    /**
     * id of category brand located in
     */
    val categoryId = reference("category_id", CategoryTable)

    /**
     * The name of brand folder
     */
    val folderName = text("folder_name")

    init {
        uniqueIndex("category_folder_index", categoryId, folderName)
    }
}

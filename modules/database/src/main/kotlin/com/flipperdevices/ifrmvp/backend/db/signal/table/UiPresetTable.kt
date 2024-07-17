package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Every UI preset file of every device
 * /CATEGORY/BRAND/MODEL/XXXX.ui.json
 */
object UiPresetTable : LongIdTable("UI_PRESET") {
    /**
     * Reference to .ir file instance
     */
    val infraredFileId = reference("infrared_file_id", InfraredFileTable)

    /**
     * The name of XXX.ui.json file
     */
    val fileName = text("file_name")
}

package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object InfraredFileToSignalTable : LongIdTable("INFRARED_FILE_TO_SIGNAL") {
    val infraredFileId = reference("infrared_file_id", InfraredFileTable)
    val signalId = reference("signal_id", SignalTable)
}

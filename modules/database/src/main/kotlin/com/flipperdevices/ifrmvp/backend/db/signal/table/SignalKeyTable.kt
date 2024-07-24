package com.flipperdevices.ifrmvp.backend.db.signal.table

import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import org.jetbrains.exposed.dao.id.LongIdTable

object SignalKeyTable : LongIdTable("SIGNAL_KEY") {
    val infraredFileId = reference("ir_file_id", InfraredFileTable)
    val signalId = reference("signal_id", SignalTable)

    /**
     * The name of brand folder
     */
    val deviceKey = enumeration<DeviceKey>("device_key")
    val remoteKeyName = text("remote_key_name")
}

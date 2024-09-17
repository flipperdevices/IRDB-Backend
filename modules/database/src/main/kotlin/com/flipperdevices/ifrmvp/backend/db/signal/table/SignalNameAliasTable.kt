package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object SignalNameAliasTable : LongIdTable("SIGNAL_NAME_ALIAS") {
    val signalId = reference("signal_id", SignalTable)
    val signalName = text("signal_name")

    init {
        uniqueIndex(signalId, signalName)
    }
}

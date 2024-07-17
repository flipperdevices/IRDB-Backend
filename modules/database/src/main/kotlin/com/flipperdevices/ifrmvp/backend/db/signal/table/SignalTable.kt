package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Every UNIQUE signal of every .ir file
 *
 * There's shouldn't be the same signal with same data
 *
 * Exception for raw signals which can be the same with parsed
 */
object SignalTable : LongIdTable("SIGNAL_TABLE") {
    val name = text("name")
    val type = text("type")
    val protocol = text("protocol").nullable()
    val address = text("address").nullable()
    val command = text("command").nullable()
    val frequency = text("frequency").nullable()
    val dutyCycle = text("duty_cycle").nullable()
    val data = text("data").nullable()

    init {
        uniqueIndex(
            "signalindex",
            type,
            protocol,
            address,
            command,
            frequency,
            dutyCycle,
            data
        )
    }
}

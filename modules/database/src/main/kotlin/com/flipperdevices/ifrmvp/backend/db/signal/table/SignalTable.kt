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
    val brandId = reference("brand_id", BrandTable)

    val type = text("type")
    val protocol = text("protocol").nullable()
    val address = text("address").nullable()
    val command = text("command").nullable()
    val frequency = text("frequency").nullable()
    val dutyCycle = text("duty_cycle").nullable()
    val data = text("data").nullable()
    val hash = text("hash")

    init {
        uniqueIndex(
            brandId,
            type,
            frequency,
            dutyCycle,
            data
        )
        uniqueIndex(
            brandId,
            type,
            protocol,
            address,
            command,
        )
    }
}

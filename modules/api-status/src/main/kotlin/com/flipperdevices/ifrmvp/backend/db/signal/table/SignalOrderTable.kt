package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object SignalOrderTable : LongIdTable("SIGNAL_ORDER_TABLE") {
    val categoryId = reference("category_id", CategoryTable)
    val brandId = reference("brand_id", BrandTable)
    val ifrFileId = reference("ifr_file_id", IfrFileTable)
    val ifrSignalId = reference("ifr_signal_id", SignalTable.id)
    val order = integer("order")

    val message = text("message")

    // Data
    val dataType = text("data_type")
    val dataIconId = text("data_icon_id").nullable()
    val dataText = text("data_text").nullable()
}

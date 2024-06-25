package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object SignalOrderTable : LongIdTable("SIGNAL_ORDER_TABLE") {
    val categoryRef = reference("category_id", CategoryTable)
    val brandRef = reference("brand_id", BrandTable)
    val ifrFileRef = reference("ifr_file_id", IfrFileTable)
    val ifrSignalRef = reference("ifr_signal_id", SignalTable.id)

    val order = integer("order")

    // Key id
    val dataKeyIdType = text("data_key_id_type")
    val dataKeyNameType = text("data_key_name_type")

    // Data
    val dataType = text("data_type")
    val dataIconId = text("data_icon_id").nullable()
    val dataText = text("data_text").nullable()
}

package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

object CategoryMetaTable : LongIdTable("CATEGORY_META") {
    val categoryId = reference("category_id", CategoryTable)
    val displayName = text("display_name")
    val singularDisplayName = text("singular_display_name")
    val iconPngBase64 = text("icn_png_base64")
    val iconSvgBase64 = text("icn_svg_base64")
}

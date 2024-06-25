package com.flipperdevices.ifrmvp.backend.db.signal.table

import com.flipperdevices.ifrmvp.backend.model.DeviceCategoryType
import org.jetbrains.exposed.dao.id.LongIdTable

object CategoryMetaTable : LongIdTable("CATEGORY_META") {
    val category = reference("category_id", CategoryTable)
    val displayName = text("display_name")
    val singularDisplayName = text("singular_display_name")
    val iconPngBase64 = text("icn_png_base64")
    val iconSvgBase64 = text("icn_svg_base64")
}

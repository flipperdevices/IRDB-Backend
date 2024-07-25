package com.flipperdevices.ifrmvp.backend.db.signal.table

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * Category meta is a table model for .meta folder
 *
 * Every category meta of every category with parent [CategoryMetaTable.categoryId]
 */
object CategoryMetaTable : LongIdTable("CATEGORY_META") {
    /**
     * id of parent category
     */
    val categoryId = reference("category_id", CategoryTable).uniqueIndex()

    /**
     * Default display name for category aka TVs, Fans
     */
    val displayName = text("display_name").uniqueIndex()

    /**
     * Singular display name aka TV, Fan which allows to translate it into other languages
     *
     */
    val singularDisplayName = text("singular_display_name").uniqueIndex()

    /**
     * .meta/icon.png converted to Base64 String
     */
    val iconPngBase64 = text("icn_png_base64")

    /**
     * .meta/icon.svg converted to Base64 String
     */
    val iconSvgBase64 = text("icn_svg_base64")
}

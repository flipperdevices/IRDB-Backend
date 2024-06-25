package com.flipperdevices.ifrmvp.backend.route.categories.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryMetaTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.model.CategoryManifest
import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class CategoriesRepositoryImpl(private val database: Database) : CategoriesRepository {
    override suspend fun getCategories(): List<DeviceCategory> {
        return transaction(database) {
            CategoryTable.selectAll()
                .map { resultRow ->
                    val categoryId = resultRow[CategoryTable.id].value
                    DeviceCategory(
                        id = categoryId,
                        meta = CategoryMetaTable.selectAll()
                            .where { CategoryMetaTable.id eq categoryId }
                            .map { metaResultRow ->
                                CategoryMeta(
                                    iconSvgBase64 = metaResultRow[CategoryMetaTable.iconSvgBase64],
                                    iconPngBase64 = metaResultRow[CategoryMetaTable.iconPngBase64],
                                    manifest = CategoryManifest(
                                        displayName = metaResultRow[CategoryMetaTable.displayName],
                                        singularDisplayName = metaResultRow[CategoryMetaTable.singularDisplayName],
                                    )
                                )
                            }.first()
                    )
                }.sortedBy { deviceCategory -> deviceCategory.meta.manifest.displayName }
        }
    }
}

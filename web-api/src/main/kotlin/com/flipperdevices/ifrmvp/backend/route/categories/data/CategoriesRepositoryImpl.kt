package com.flipperdevices.ifrmvp.backend.route.categories.data

import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class CategoriesRepositoryImpl(private val database: Database) : CategoriesRepository {
    override suspend fun getCategories(): List<DeviceCategory> {
        return transaction(database) {
            CategoryTable.selectAll()
                .map { resultRow ->
                    DeviceCategory(
                        id = resultRow[CategoryTable.id].value,
                        displayName = resultRow[CategoryTable.displayName],
                        type = resultRow[CategoryTable.deviceType]
                    )
                }.sortedBy { deviceCategory -> deviceCategory.displayName }
        }
    }
}

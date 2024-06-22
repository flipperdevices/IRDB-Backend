package com.flipperdevices.ifrmvp.backend.route.categories.data

import com.flipperdevices.ifrmvp.backend.model.DeviceCategory

internal interface CategoriesRepository {
    suspend fun getCategories(): List<DeviceCategory>
}

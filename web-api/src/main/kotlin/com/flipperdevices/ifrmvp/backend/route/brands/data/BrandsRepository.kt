package com.flipperdevices.ifrmvp.backend.route.brands.data

import com.flipperdevices.ifrmvp.backend.model.BrandModel

internal interface BrandsRepository {
    suspend fun getBrands(categoryId: Long, query: String): List<BrandModel>
}

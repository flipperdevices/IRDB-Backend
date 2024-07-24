package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.generator.config.category.api.AllCategoryConfigGenerator

interface CategoryConfigRepository {
    fun getOrNull(categoryType: CategoryType, index: Int): CategoryConfiguration.OrderModel?
}

object InstantCategoryConfigRepository : CategoryConfigRepository {
    override fun getOrNull(
        categoryType: CategoryType,
        index: Int
    ): CategoryConfiguration.OrderModel? {
        return AllCategoryConfigGenerator
            .generate(categoryType)
            .orders
            .getOrNull(index)
    }
}

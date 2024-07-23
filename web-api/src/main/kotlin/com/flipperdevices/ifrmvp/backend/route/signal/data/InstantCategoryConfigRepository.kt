package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.generator.config.category.api.AllCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryType

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

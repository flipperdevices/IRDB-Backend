package com.flipperdevices.ifrmvp.backend.route.signal.data

import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver

interface CategoryConfigRepository {
    fun getOrNull(categoryType: CategoryType, index: Int): CategoryConfiguration.OrderModel?
}

object IRDBCategoryConfigRepository : CategoryConfigRepository {
    override fun getOrNull(categoryType: CategoryType, index: Int): CategoryConfiguration.OrderModel? {
        return ParserPathResolver
            .categoryConfiguration(categoryType.folderName)
            .orders
            .getOrNull(index)
    }
}

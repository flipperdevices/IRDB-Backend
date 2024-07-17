package com.flipperdevices.ifrmvp.backend.db.signal.dao

import com.flipperdevices.ifrmvp.backend.model.BrandModel
import com.flipperdevices.ifrmvp.backend.model.DeviceCategory
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel

interface TableDao {
    suspend fun getCategoryById(categoryId: Long): DeviceCategory
    suspend fun getBrandById(brandId: Long): BrandModel
    suspend fun ifrFileById(irFileId: Long): IfrFileModel

    companion object {
        suspend fun TableDao.getCategoryByBrandId(brandId: Long): DeviceCategory {
            val brandModel = getBrandById(brandId)
            return getCategoryById(brandModel.categoryId)
        }
    }
}

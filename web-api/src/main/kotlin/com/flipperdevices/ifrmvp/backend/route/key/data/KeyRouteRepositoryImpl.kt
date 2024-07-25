package com.flipperdevices.ifrmvp.backend.route.key.data

import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import java.io.File

class KeyRouteRepositoryImpl(
    private val tableDao: TableDao
) : KeyRouteRepository {
    override suspend fun getIfrFile(ifrFileId: Long): File {
        val ifrFileModel = tableDao.ifrFileById(ifrFileId)
        val brandModel = tableDao.getBrandById(ifrFileModel.brandId)
        val categoryModel = tableDao.getCategoryById(brandModel.categoryId)

        val categoryFolderName = categoryModel.folderName
        val brandFolderName = brandModel.folderName
        val file = ParserPathResolver.ifrFile(
            category = categoryFolderName,
            brand = brandFolderName,
            ifrFolderName = ifrFileModel.folderName
        )
        return file
    }
}

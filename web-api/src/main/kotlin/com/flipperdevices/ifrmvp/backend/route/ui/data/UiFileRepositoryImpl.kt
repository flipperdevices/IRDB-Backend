package com.flipperdevices.ifrmvp.backend.route.ui.data

import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.db.signal.table.UiPresetTable
import com.flipperdevices.ifrmvp.backend.model.UiPresetModel
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UiFileRepositoryImpl(
    private val tableDao: TableDao,
    private val database: Database,
) : UiFileRepository {
    override suspend fun getUiFileModelOrNull(ifrFileId: Long): UiPresetModel? {
        val uiFileModel = transaction(database) {
            UiPresetTable.selectAll()
                .where { UiPresetTable.infraredFileId eq ifrFileId }
                .map {
                    UiPresetModel(
                        id = it[UiPresetTable.id].value,
                        fileName = it[UiPresetTable.fileName],
                        infraredFileId = it[UiPresetTable.infraredFileId].value
                    )
                }
                .firstOrNull()
        }
        return uiFileModel
    }

    override suspend fun getUiFileContent(uiFileModel: UiPresetModel): String {
        val ifrFileModel = tableDao.ifrFileById(uiFileModel.infraredFileId)
        val brandModel = tableDao.getBrandById(ifrFileModel.brandId)
        val categoryModel = tableDao.getCategoryById(brandModel.categoryId)
        val brandFolderName = brandModel.folderName
        val categoryFolderName = categoryModel.folderName
        val uiPresetFile = ParserPathResolver.uiPresetFile(
            category = categoryFolderName,
            brand = brandFolderName,
            presetFileName = uiFileModel.fileName,
            ifrFolderName = ifrFileModel.fileName.replace(".ir", "")
        )
        if (!uiPresetFile.exists()) error("Preset file ${uiPresetFile.absolutePath} not exists")
        return uiPresetFile.readText()
    }
}

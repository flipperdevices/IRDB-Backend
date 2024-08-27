package com.flipperdevices.ifrmvp.kenerator.ui

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.kenerator.ui.category.DefaultUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.camera.CameraMap
import com.flipperdevices.ifrmvp.kenerator.ui.category.fan.FanMap
import com.flipperdevices.ifrmvp.kenerator.ui.category.tv.TvMap
import com.flipperdevices.ifrmvp.model.PagesLayout
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
import java.io.File

class UiGeneratorImpl(private val tableDao: TableDao) : UiGenerator {

    private suspend fun getIfrFile(ifrFileId: Long): File {
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


    override suspend fun generate(irFileId: Long): PagesLayout {
        val infraredFileModel = tableDao.ifrFileById(irFileId)
        val brand = tableDao.getBrandById(infraredFileModel.brandId)
        val category = tableDao.getCategoryById(brand.categoryId)
        val categoryType = CategoryType.entries
            .find { it.folderName == category.folderName }
            ?: error("Could not find category with name ${category.folderName}")

        val signals = getIfrFile(irFileId)
            .readText()
            .let(FlipperFileFormat.Companion::fromFileContent)
            .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
        println("infraredFileModel: ${category.folderName}/${brand.folderName}/${infraredFileModel.fileName} ")

        return when (categoryType) {
            CategoryType.A_V_RECEIVER -> DefaultUiGenerator(TvMap()).generate(signals)
            CategoryType.AIR_PURIFIERS -> DefaultUiGenerator(FanMap()).generate(signals)
            CategoryType.BOX -> DefaultUiGenerator(TvMap()).generate(signals)
            CategoryType.CAMERA -> DefaultUiGenerator(CameraMap()).generate(signals)
            CategoryType.DVD -> DefaultUiGenerator(TvMap()).generate(signals)
            CategoryType.FAN -> DefaultUiGenerator(FanMap()).generate(signals)
            CategoryType.PROJECTOR -> DefaultUiGenerator(TvMap()).generate(signals)
            CategoryType.TVS -> DefaultUiGenerator(TvMap()).generate(signals)
        }
    }
}

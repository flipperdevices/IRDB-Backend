package com.flipperdevices.ifrmvp.kenerator.ui

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.kenerator.ui.category.airpurifier.AirPurifierUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.camera.CameraUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.avreceiver.AvReceiverUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.box.BoxUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.dvd.DvdUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.fan.FanUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.projector.ProjectorUiGenerator
import com.flipperdevices.ifrmvp.kenerator.ui.category.tv.TvUiGenerator
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
            CategoryType.A_V_RECEIVER -> AvReceiverUiGenerator().generate(signals)
            CategoryType.AIR_PURIFIERS -> AirPurifierUiGenerator().generate(signals)
            CategoryType.BOX -> BoxUiGenerator().generate(signals)
            CategoryType.CAMERA -> CameraUiGenerator().generate(signals)
            CategoryType.DVD -> DvdUiGenerator().generate(signals)
            CategoryType.FAN -> FanUiGenerator().generate(signals)
            CategoryType.PROJECTOR -> ProjectorUiGenerator().generate(signals)
            CategoryType.TVS -> TvUiGenerator().generate(signals)
        }
    }
}

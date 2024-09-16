package com.flipperdevices.ifrmvp.parser.util

import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import com.flipperdevices.ifrmvp.backend.model.CategoryConfiguration
import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.backend.model.DeviceConfiguration
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object ParserPathResolver {
    /**
     * The root folder of file database
     */
    val irDbFolderFolder: File
        get() = File(EnvKonfig.IR_DATABASE_PATH)

    /**
     * Get Categories folder
     */
    val categoriesFolder: File
        get() = irDbFolderFolder.resolve("categories")

    /**
     * Return file list of available categories
     */
    val categories: List<File>
        get() = categoriesFolder.listFiles()
            .orEmpty()
            .filter { it.isDirectory }
            .filter { it.name != ".DS_Store" }

    /**
     * Get path to specific category
     * @param category the name of category
     */
    fun categoryPath(category: String): File {
        return categoriesFolder.resolve(category)
    }

    fun categoryConfiguration(category: String): CategoryConfiguration {
        val file = categoryConfigurationFile(category = category)
        return Json.decodeFromString(file.readText())
    }

    fun categoryConfigurationFile(category: String): File {
        return categoryMetaPath(category).resolve("config.json")
    }

    /**
     * Get all brands of current category
     * @param category the name of category
     */
    fun brands(category: String): List<File> {
        return categoryPath(category).listFiles()
            .orEmpty()
            .filter { it.isDirectory }
            .filter { !it.name.startsWith(".") }
    }

    /**
     * Path to specific brand
     * @param category the name of category
     * @param brand the name of brand
     */
    fun brandPath(category: String, brand: String): File {
        return categoryPath(category).resolve(brand)
    }

    /**
     * Path to meta folder of specific brand
     * @param category the name of category
     */
    fun categoryMetaPath(category: String): File {
        return categoryPath(category).resolve(".meta")
    }

    /**
     * Get meta of current brand
     * @param category the name of category
     */
    @OptIn(ExperimentalEncodingApi::class)
    fun categoryMeta(category: String): CategoryMeta {
        val metaFolder = categoryMetaPath(category)
        val iconPng = metaFolder.resolve("icon.png")
        val iconSvg = metaFolder.resolve("icon.svg")
        val manifest = metaFolder.resolve("manifest.json")
        return CategoryMeta(
            iconPngBase64 = Base64.encode(iconPng.readBytes()),
            iconSvgBase64 = Base64.encode(iconSvg.readBytes()),
            manifest = Json.decodeFromString(manifest.readText())
        )
    }

    /**
     * Get list of all ir files in selected brand
     * @param category category name of brand
     * @param brand the name of brand
     */
    fun brandIfrFiles(category: String, brand: String): List<File> {
        return brandPath(category, brand)
            .listFiles()
            .orEmpty()
            .flatMap { irFolder -> irFolder.listFiles().orEmpty().filter { it.extension == "ir" } }
    }

    /**
     * Get specific ifr file
     * @param category category name of brand
     * @param brand the name of brand
     * @param ifrFolderName the name of specific controller id, it's ifr file name
     */
    fun ifrFile(category: String, brand: String, ifrFolderName: String): File {
        val folder = brandPath(category, brand).resolve(ifrFolderName)
        return folder.resolve("$ifrFolderName.ir")
    }

    fun irFileConfiguration(
        category: String,
        brand: String,
        ifrFolderName: String
    ): DeviceConfiguration {
        val file = irFileConfigurationFile(
            category = category,
            brand = brand,
            ifrFolderName = ifrFolderName
        )
        return kotlin.runCatching { Json.decodeFromString<DeviceConfiguration>(file.readText()) }
            .onFailure { println("Could not parse file ${file.absoluteFile}") }
            .getOrThrow()
    }

    fun irFileConfigurationFile(
        category: String,
        brand: String,
        ifrFolderName: String
    ): File {
        return brandPath(category, brand).resolve(ifrFolderName).resolve("config.json")
    }

    /**
     * Get specific ui preset file
     * @param category category name of brand
     * @param brand the name of brand
     * @param ifrFolderName the name of specific controller id, it's ifr file name
     * @param presetFileName name of preset with extension
     */
    fun uiPresetFile(
        category: String,
        brand: String,
        ifrFolderName: String,
        presetFileName: String
    ): File {
        val folder = brandPath(category, brand).resolve(ifrFolderName)
        return folder.resolve(presetFileName)
    }
}

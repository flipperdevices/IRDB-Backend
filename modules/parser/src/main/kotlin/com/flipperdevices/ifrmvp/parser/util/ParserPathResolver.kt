package com.flipperdevices.ifrmvp.parser.util

import com.flipperdevices.ifrmvp.backend.buildkonfig.BuildKonfig
import com.flipperdevices.ifrmvp.backend.model.CategoryMeta
import com.flipperdevices.ifrmvp.parser.model.OrderModel
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object ParserPathResolver {
    /**
     * The root folder of file database
     */
    val irDbFolderFolder: File
        get() = File(BuildKonfig.IR_FOLDER_PATH)
            .also { println(it.absolutePath) }

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
     * Get orders of selected controller
     * @param category category name of brand
     * @param brand the name of brand
     * @param controller the name of controller
     */
    fun controllerOrders(
        category: String,
        brand: String,
        controller: String
    ): List<OrderModel> {
        val controllerFolder = brandPath(category, brand).resolve(controller)
        val orders = controllerFolder.resolve("orders.json")
        if (!orders.exists()) return emptyList()
        return Json.decodeFromString<List<OrderModel>>(orders.readText())
    }
}

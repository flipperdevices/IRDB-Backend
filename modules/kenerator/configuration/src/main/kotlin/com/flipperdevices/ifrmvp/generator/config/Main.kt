package com.flipperdevices.ifrmvp.generator.config

import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.generator.config.category.api.AllCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.device.api.DefaultDeviceConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    prettyPrint = true
}

private fun generateCategoriesConfigFiles() {
    ParserPathResolver
        .categories
        .onEach { categoryFolder ->
            val categoryType = CategoryType.entries.first { it.folderName == categoryFolder.name }
            val config = AllCategoryConfigGenerator.generate(categoryType)
            val configFile = ParserPathResolver.categoryMetaPath(categoryFolder.name).resolve("config.json")
            if (configFile.exists()) configFile.delete()
            configFile.createNewFile()
            val string = json.encodeToString(config)
            configFile.writeText(string)
        }
}

private fun generateDevicesConfigFiles() {
    ParserPathResolver
        .categories
        .onEach { categopryFolder ->
            ParserPathResolver.brands(categopryFolder.name)
                .onEach { brandFolder ->
                    ParserPathResolver.brandIfrFiles(
                        category = categopryFolder.name,
                        brand = brandFolder.name
                    ).onEach { irFile ->
                        val config = DefaultDeviceConfigGenerator(AnyDeviceKeyNamesProvider)
                            .generate(irFile)
                        if (config.keyMap.isEmpty()) error("Config file for ${irFile} is empty")
                        val configFile = irFile.parentFile.resolve("config.json")
                        val string = json.encodeToString(config)
                        configFile.writeText(string)
                    }
                }
        }
}

fun main() {
    generateCategoriesConfigFiles()
    generateDevicesConfigFiles()
}

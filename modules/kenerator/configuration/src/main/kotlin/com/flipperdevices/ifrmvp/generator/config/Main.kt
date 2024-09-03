package com.flipperdevices.ifrmvp.generator.config

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.ifrmvp.backend.model.CategoryType
import com.flipperdevices.ifrmvp.backend.model.DeviceKey
import com.flipperdevices.ifrmvp.generator.config.category.api.AirPurifierCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.AvReceiverCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.BoxCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.CameraCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt
import com.flipperdevices.ifrmvp.generator.config.category.api.DeviceKeyExt.getAllowedCategories
import com.flipperdevices.ifrmvp.generator.config.category.api.DvdCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.FanCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.ProjectorCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.api.TvsCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.device.api.DefaultDeviceConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import com.flipperdevices.infrared.editor.util.InfraredMapper
import com.flipperdevices.infrared.editor.viewmodel.InfraredKeyParser
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
            val config = when (categoryType) {
                CategoryType.A_V_RECEIVER -> AvReceiverCategoryConfigGenerator.generate(categoryType)
                CategoryType.AIR_PURIFIERS -> AirPurifierCategoryConfigGenerator.generate(categoryType)
                CategoryType.BOX -> BoxCategoryConfigGenerator.generate(categoryType)
                CategoryType.CAMERA -> CameraCategoryConfigGenerator.generate(categoryType)
                CategoryType.DVD -> DvdCategoryConfigGenerator.generate(categoryType)
                CategoryType.FAN -> FanCategoryConfigGenerator.generate(categoryType)
                CategoryType.PROJECTOR -> ProjectorCategoryConfigGenerator.generate(categoryType)
                CategoryType.TVS -> TvsCategoryConfigGenerator.generate(categoryType)
            }
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

fun printAllKeys() {

    val keyNames = DeviceKey.entries.associateWith { AnyDeviceKeyNamesProvider.getKeyNames(it) }
    val namesToCategories = mutableMapOf<String, MutableSet<String>>()
    val nameToCount = mutableMapOf<String, Int>()
    ParserPathResolver
        .categories
        .filter { it.name.equals("TVs", true) }
        .flatMap { categoryFolder ->
            ParserPathResolver.brands(categoryFolder.name)
                .flatMap { brandFolder ->
                    ParserPathResolver.brandIfrFiles(
                        category = categoryFolder.name,
                        brand = brandFolder.name
                    ).flatMap { irFile ->
                        val signals = irFile
                            .readText()
                            .let(FlipperFileFormat.Companion::fromFileContent)
                            .let(InfraredKeyParser::mapParsedKeyToInfraredRemotes)
                            .onEach { signal ->
                                val set = namesToCategories[signal.name.lowercase()] ?: mutableSetOf()
                                set.add(categoryFolder.name)
                                namesToCategories[signal.name.lowercase()] = set
                                nameToCount[signal.name.lowercase()] = (nameToCount[signal.name.lowercase()] ?: 0) + 1
                            }
                        signals
                    }
                }
        }.distinctBy { it.name.lowercase() }
//        .sortedBy { it.name.lowercase() }
        .sortedByDescending { nameToCount[it.name.lowercase()] }
        .onEach { key ->
            val filteredMap = keyNames.filterValues { it.map { it.lowercase() }.contains(key.name.lowercase()) }
            println("${key.name.lowercase()} -> ${nameToCount[key.name.lowercase()]} ${namesToCategories[key.name.lowercase()]}  -> ${filteredMap.map { (k, v) -> "$k - ${k.getAllowedCategories()} -${v}" }}")
        }

}

private fun filterSameInfraredFiles() {
    ParserPathResolver.categories
        .forEach { category ->
            ParserPathResolver.brands(category.name)
                .forEach { brand ->
                    ParserPathResolver.brandIfrFiles(
                        category = category.name,
                        brand = brand.name
                    ).forEach { irFile ->
                        val remotes = InfraredMapper.parseRemotes(irFile)
                        val distinctRemotes = remotes.distinctBy { it }
                        val sameRemotesCount = remotes.size - distinctRemotes.size
                        if (sameRemotesCount < 0) error("wtf")
                        if (sameRemotesCount == 0) return@forEach
                        irFile.writeText(InfraredMapper.toInfraredFormat(distinctRemotes))
                    }
                }
        }
}

fun main() {
//    generateCategoriesConfigFiles()
//    generateDevicesConfigFiles()
//    printAllKeys()
    filterSameInfraredFiles()
}

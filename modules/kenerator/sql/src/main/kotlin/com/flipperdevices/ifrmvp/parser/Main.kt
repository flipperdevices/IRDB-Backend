package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import com.flipperdevices.ifrmvp.parser.di.ParserModule
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlinx.coroutines.runBlocking

fun main() {
    runParser()
//    fixIrdbStructure()
}

fun runParser() {
    val parserModule = ParserModule.Default(
        signalApiModule = SignalApiModule.Default(
            signalDbConnection = EnvKonfig.signalDatabaseConnection
        )
    )
    runBlocking {
        parserModule.fillerController.fillDatabase()
    }
}

fun fixIrdbStructure() {
    val folder = ParserPathResolver.irDbFolderFolder
        .parentFile
        .resolve("Flipper-IRDB")
    val requiredCategories = listOf(
        "Air_Purifiers",
        "Cameras",
        "DVD_Players",
        "Fans",
        "Projectors",
        "TVs"
    )
    // delete non-required categories
    folder.listFiles()
        .orEmpty()
        .filter { !requiredCategories.contains(it.name) }
        .onEach { it.deleteRecursively() }
    folder.listFiles()
        .orEmpty()
        .filter { it.isDirectory }
        .filter { requiredCategories.contains(it.name) }
        .onEach { category ->
            // remove non-directory files in categories
            category.listFiles()
                .orEmpty()
                .filter { !it.isDirectory }
                .onEach { it.delete() }
            category.listFiles()
                .orEmpty()
                .filter { it.isDirectory }
                .filter { !it.name.contains("unknown",true) }
                .onEach { brand ->
                    brand.listFiles()
                        .orEmpty()
                        .filter { it.isFile }
                        .filter { it.extension == "ir" }
                        .filter { !it.name.contains("unknown", true) }
                        .filter { !it.name.contains("config", true) }
                        .filter { !it.nameWithoutExtension.equals(brand.name, true) }
                        .filter { it.nameWithoutExtension.any { it.isDigit() } }
                        .onEach { model ->
                            fun String.replaceInvalidChars(): String {
                                return this
                                    .replace("-","")
                                    .replace(" ","_")
                            }
                            val modelFolder = brand.resolve(model.nameWithoutExtension.replaceInvalidChars())
                            modelFolder.mkdir()
                            model.copyTo(modelFolder.resolve(model.name.replaceInvalidChars()))
                            model.delete()
                        }

                    // remove non-ir files
                    brand.listFiles()
                        .orEmpty()
                        .filter { it.extension!=".ir" }
                        .onEach { it.delete() }
                    // remove non-filtered files and empty folders
                    brand.listFiles()
                        .orEmpty()
                        .filterNot { !it.name.contains("unknown", true) }
                        .filterNot { !it.name.contains("config", true) }
                        .filterNot { !it.nameWithoutExtension.equals(brand.name, true) }
                        .filterNot { it.nameWithoutExtension.any { it.isDigit() } }
                        .onEach {
                            it.delete()
                            if (it.parentFile.listFiles().isNullOrEmpty()) it.parentFile.deleteRecursively()
                        }
                    // remove empty folders
                    brand.listFiles()
                        .orEmpty()
                        .filter { it.isDirectory }
                        .filter { it.listFiles().isNullOrEmpty() }
                        .onEach { it.deleteRecursively() }
                }

            category.listFiles()
                .orEmpty()
                .filterNot { !it.name.contains("unknown",true) }
                .onEach { it.deleteRecursively() }
            // remove empty folders
            category.listFiles()
                .orEmpty()
                .filter { it.isDirectory }
                .filter { it.listFiles().isNullOrEmpty() }
                .onEach { it.deleteRecursively() }
        }
}

/**
 * Fix directory structure
 */
fun mainUnused() {
    val categories = ParserPathResolver.irDbFolderFolder.resolve("categories")
    categories.listFiles().orEmpty().forEach { categoryFolder ->
        categoryFolder
            .listFiles()
            .orEmpty()
            .forEach { brandFolder ->
                brandFolder.listFiles()
                    .orEmpty()
                    .filter { it.extension == "ir" }
                    .forEach { brandFile ->
                        val controllerFolder = brandFolder.resolve(brandFile.nameWithoutExtension)
                        controllerFolder.mkdir()
                        brandFile.copyTo(controllerFolder.resolve(brandFile.name))
                        brandFile.delete()
                    }
            }
    }
}

@Suppress("NestedBlockDepth")
fun fixMiRemoteDatabase() {
    val miRemoteFolder = ParserPathResolver.irDbFolderFolder
        .parentFile
        .resolve("mi_remote_database")
    val categories = miRemoteFolder.resolve("categories")
    categories.listFiles()
        .orEmpty()
        .forEach { categoryFolder ->
            categoryFolder.listFiles()
                .orEmpty()
                .filter { it.isDirectory }
                .onEach { it.deleteRecursively() }

            categoryFolder.listFiles()
                .orEmpty()
                .filter { !it.isDirectory }
                .filter { it.extension == "ir" }
                .forEach { irFile ->
                    val brandName = irFile.name.let { name ->
                        val splitByUnderscore = name.split("_")
                        val i = splitByUnderscore.indexOfFirst { it.toIntOrNull() != null }
                        if (i == -1) {
                            when {
                                name.contains("kk") -> name.split("_kk").first()
                                name.contains("mi") -> name.split("_mi").first()
                                name.contains("mx") -> name.split("_mx").first()
                                name.contains("xm") -> name.split("_xm").first()
                                else -> splitByUnderscore.first()
                            }
                        } else {
                            splitByUnderscore.joinToString(separator = "_", limit = i, truncated = "").removeSuffix("_")
                        }
                    }
                    val brandFolder = categoryFolder.resolve(brandName)
                    if (!brandFolder.exists()) brandFolder.mkdir()

                    val controlFolder = brandFolder.resolve(irFile.nameWithoutExtension)
                    if (!controlFolder.exists()) controlFolder.mkdir()

                    val controlFile = controlFolder.resolve(irFile.name)
                    irFile.copyTo(controlFile)
                    irFile.delete()
                }
        }
}

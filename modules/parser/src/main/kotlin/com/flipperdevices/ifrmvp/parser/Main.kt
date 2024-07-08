package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.envkonfig.EnvKonfig
import com.flipperdevices.ifrmvp.parser.di.ParserModule
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlinx.coroutines.runBlocking

fun main() {
    runParser()
}

fun runParser() {
    val parserModule = ParserModule.Default(
        signalApiModule = SignalApiModule.Default(
            signalDbConnection = EnvKonfig.signalDatabaseConnection
        )
    )
    runBlocking {
        parserModule.fillerController.fillDatabase().join()
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
                        if (i == -1) when {
                            name.contains("kk") -> name.split("_kk").first()
                            name.contains("mi") -> name.split("_mi").first()
                            name.contains("mx") -> name.split("_mx").first()
                            name.contains("xm") -> name.split("_xm").first()
                            else -> splitByUnderscore.first()
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





























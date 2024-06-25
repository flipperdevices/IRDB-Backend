package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.backend.db.signal.di.SignalApiModule
import com.flipperdevices.ifrmvp.backend.envkonfig.DBConnection
import com.flipperdevices.ifrmvp.parser.di.ParserModule
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    val parserModule = ParserModule.Default(
        signalApiModule = SignalApiModule.Default(
            signalDbConnection = DBConnection.H2("signal_database")
        )
    )
    runBlocking {
        parserModule.parserController.start().join()
    }
}

/**
 * Fix directory structure
 */
fun mainUnused() {
    val irDbFolder = File("/Users/romanmakeev/Desktop/GitHub/TestUiDesktop/irdb")
    val categories = irDbFolder.resolve("categories")
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

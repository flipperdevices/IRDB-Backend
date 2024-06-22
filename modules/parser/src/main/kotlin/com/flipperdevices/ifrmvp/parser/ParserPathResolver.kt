package com.flipperdevices.ifrmvp.parser

import java.io.File

object ParserPathResolver {
    // todo
    val irDbFolderFolder: File
        get() = File("/Users/romanmakeev/Desktop/GitHub/TestUiDesktop/irdb")
            .also { println(it.absolutePath) }

    val categoriesFolder: File
        get() = irDbFolderFolder.resolve("categories")

    val categories: List<File>
        get() = categoriesFolder.listFiles()
            .orEmpty()
            .filter { it.isDirectory }

    fun categoryPath(category: String): File {
        return categoriesFolder.resolve(category)
    }

    fun brands(category: String): List<File> {
        return categoryPath(category).listFiles()
            .orEmpty()
            .filter { it.isDirectory }
    }

    fun brandPath(category: String, brand: String): File {
        return categoryPath(category).resolve(brand)
    }

    fun brandIfrFiles(category: String, brand: String): List<File> {
        return brandPath(category, brand)
            .listFiles()
            .orEmpty()
            .filter { it.extension == "ir" }
    }
}

package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ParserPathResolverTest {
    @Test
    fun test() {
        assertTrue("IR Database folder doesn't exists") {
            ParserPathResolver.irDbFolderFolder.exists()
        }
        assertTrue("Categories are empty") {
            ParserPathResolver.categoriesFolder.listFiles().orEmpty().isNotEmpty()
        }
        ParserPathResolver.categories
            .onEach { categoryFolder ->
                val meta = runCatching {
                    ParserPathResolver.categoryMeta(categoryFolder.name)
                }.getOrNull()
                assertNotNull(meta, "Meta of category ${categoryFolder.name} is null")
            }
            .onEach { categoryFolder ->
                assertTrue("Brands of ${categoryFolder.name} are empty") {
                    ParserPathResolver.brands(categoryFolder.name).isNotEmpty()
                }
            }
            .onEach { categoryFolder ->
                ParserPathResolver.brands(categoryFolder.name)
                    .onEach { brandFolder ->
                        assertTrue(
                            "Brands ir files are empty brand=${brandFolder.name}; category=${categoryFolder.name}"
                        ) {
                            ParserPathResolver.brandIfrFiles(categoryFolder.name, brandFolder.name)
                                .onEach { ifrFile ->
                                    val ifrFolder = ifrFile.parentFile
                                    assertTrue("Ifr file not exists ${ifrFolder.name} ${ifrFolder.absolutePath}") {
                                        ParserPathResolver.ifrFile(
                                            category = categoryFolder.name,
                                            brand = brandFolder.name,
                                            ifrFolderName = ifrFolder.name
                                        ).exists()
                                    }
                                }
                                .isNotEmpty()
                        }
                    }
            }
    }
}

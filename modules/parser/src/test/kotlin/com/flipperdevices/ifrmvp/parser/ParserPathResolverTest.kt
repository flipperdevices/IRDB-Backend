package com.flipperdevices.ifrmvp.parser

import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import kotlin.test.Test
import kotlin.test.assertTrue

class ParserPathResolverTest {
    @Test
    fun test() {
        assertTrue("IR Database folder doesn't exists") {
            ParserPathResolver.irDbFolderFolder.exists()
        }
        assertTrue("Categories is empty") {
            ParserPathResolver.categoriesFolder.listFiles().orEmpty().isNotEmpty()
        }
        assertTrue("Brands is empty") {
            ParserPathResolver.brands(ParserPathResolver.categories.first().name).isNotEmpty()
        }
    }
}

package com.flipperdevices.ifrmvp.backend.route.key.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.table.BrandTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.CategoryTable
import com.flipperdevices.ifrmvp.backend.db.signal.table.IfrFileTable
import com.flipperdevices.ifrmvp.backend.model.IfrFileModel
import com.flipperdevices.ifrmvp.parser.util.ParserPathResolver
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class KeyRouteRegistry(
    private val database: Database
) : RouteRegistry {

    private fun Routing.statusRoute() {
        get(
            path = "key",
            builder = { with(KeySwagger) { createSwaggerDefinition() } },
            body = {

                val ifrFileId = context.parameters["ifr_file_id"]?.toLongOrNull()
                val ifrFileModel = transaction(database) {
                    IfrFileTable.selectAll()
                        .where { IfrFileTable.id eq ifrFileId }
                        .map {
                            IfrFileModel(
                                id = it[IfrFileTable.id].value,
                                categoryId = it[IfrFileTable.categoryId].value,
                                brandId = it[IfrFileTable.brandId].value,
                                fileName = it[IfrFileTable.fileName]
                            )
                        }
                        .firstOrNull()
                        ?: error("Ir file with id $ifrFileId not found!")
                }
                val categoryFolderName = transaction(database) {
                    CategoryTable.select(CategoryTable.folderName)
                        .where { CategoryTable.id eq ifrFileModel.categoryId }
                        .map { it[CategoryTable.folderName] }
                        .firstOrNull()
                        ?: error("Category with id ${ifrFileModel.categoryId} not found!")
                }
                val brandFolderName = transaction(database) {
                    BrandTable.select(BrandTable.displayName)
                        .where { BrandTable.id eq ifrFileModel.brandId }
                        .map { it[BrandTable.displayName] }
                        .firstOrNull()
                        ?: error("Brand with id ${ifrFileModel.brandId} not found!")
                }
                println("Category :${categoryFolderName} brand: ${brandFolderName} ifr: ${ifrFileModel.fileName}")
                val file = ParserPathResolver.ifrFile(
                    category = categoryFolderName,
                    brand = brandFolderName,
                    ifrFolderName = ifrFileModel.fileName.replaceFirst(".ir","")//todo
                )
                if (!file.exists()) error("Ifr file doesn't exists! ${file.absolutePath}")
                context.respond(file.readText())
            }
        )
    }

    override fun register(routing: Routing) {
        routing.statusRoute()
    }
}

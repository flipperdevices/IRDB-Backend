package com.flipperdevices.ifrmvp.backend.route.configgen.presentation

import com.flipperdevices.ifrmvp.backend.core.route.RouteRegistry
import com.flipperdevices.ifrmvp.backend.db.signal.dao.TableDao
import com.flipperdevices.ifrmvp.backend.route.key.data.KeyRouteRepository
import com.flipperdevices.ifrmvp.backend.route.key.presentation.KeySwagger
import com.flipperdevices.ifrmvp.generator.config.category.api.AllCategoryConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.category.model.CategoryType
import com.flipperdevices.ifrmvp.generator.config.device.api.DefaultDeviceConfigGenerator
import com.flipperdevices.ifrmvp.generator.config.device.api.any.AnyDeviceKeyNamesProvider
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

internal class ConfigGenRouteRegistry(
    private val keyRouteRepository: KeyRouteRepository,
    private val tableDao: TableDao
) : RouteRegistry {
    private fun Routing.deviceConfigRoute() {
        get(
            path = "configuration/device",
            builder = { with(KeySwagger) { createSwaggerDefinition() } },
            body = {
                val ifrFileId = context.parameters["ifr_file_id"]?.toLongOrNull() ?: -1
                val file = keyRouteRepository.getIfrFile(ifrFileId)
                if (!file.exists()) error("Ifr file doesn't exists! ${file.absolutePath}")
                context.respond(DefaultDeviceConfigGenerator(AnyDeviceKeyNamesProvider).generate(file))
            }
        )
    }

    private fun Routing.categoryConfigRoute() {
        get(
            path = "configuration/category",
            builder = { with(KeySwagger) { createSwaggerDefinition() } },
            body = {
                val categoryId = context.parameters["category_id"]?.toLongOrNull() ?: -1
                val category = tableDao.getCategoryById(categoryId)
                val categoryType = CategoryType
                    .entries
                    .firstOrNull { entry -> entry.folderName == category.folderName }
                    ?: error("Category with fodlerName ${category.folderName} not found!")
                val configuration = AllCategoryConfigGenerator.generate(categoryType)
                context.respond(configuration)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.deviceConfigRoute()
        routing.categoryConfigRoute()
    }
}

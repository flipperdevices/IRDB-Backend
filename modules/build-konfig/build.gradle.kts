@file:Suppress("UnusedPrivateMember")

import ru.astrainteractive.gradleplugin.property.PropertyValue.Companion.secretProperty
import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    kotlin("jvm")
    id("ru.astrainteractive.gradleplugin.java.core")
    id("com.github.gmazzo.buildconfig")
}

buildConfig {
    className("BuildKonfig")
    packageName("${requireProjectInfo.group}.buildkonfig")
    useKotlinOutput { internalVisibility = false }

    buildConfigField(
        type = String::class.java,
        name = "VERSION_NAME",
        value = requireProjectInfo.versionString
    )
    buildConfigField(
        type = String::class.java,
        name = "GROUP",
        value = requireProjectInfo.group
    )
    buildConfigField(
        type = String::class.java,
        name = "PROJECT_NAME",
        value = requireProjectInfo.name
    )
    buildConfigField(
        type = String::class.java,
        name = "PROJECT_DESC",
        value = requireProjectInfo.description
    )
    buildConfigField(
        type = String::class.java,
        name = "IR_FOLDER_PATH",
        value = rootProject.file("database").absolutePath
    )
}

afterEvaluate {
    // Backend
    listOf("FBACKEND_HOST", "FBACKEND_PORT")
        // DB Type
        .plus(listOf("FBACKEND_DB_TYPE"))
        // H2 DB
        .plus(listOf("FBACKEND_DB_NAME"))
        .mapNotNull { key ->
            secretProperty(key).value
                .getOrNull()
                .also { logger.error("Secret proeprty: $key value: $it") }
                ?.let { value -> key to value }
        }
        .forEach { (k, v) -> System.setProperty(k, v) }
}

dependencies {
    // Log
    implementation(libs.logback.classic)
    // kdi
    implementation(libs.klibs.kdi)
    // ktor
    implementation(libs.ktor.server.auth.jwt)
}

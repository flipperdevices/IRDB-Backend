@file:Suppress("UnusedPrivateMember")

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

    buildConfigField(String::class.java, "VERSION_NAME", requireProjectInfo.versionString)
    buildConfigField(String::class.java, "GROUP", requireProjectInfo.group)
}

dependencies {
    // Log
    implementation(libs.logback.classic)
    // kdi
    implementation(libs.klibs.kdi)
    // ktor
    implementation(libs.ktor.server.auth.jwt)
}

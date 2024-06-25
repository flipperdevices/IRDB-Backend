plugins {
    java
    `java-library`
    alias(libs.plugins.gradle.shadow) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.gradle.buildconfig) apply false
    alias(libs.plugins.gradle.android.library) apply false

    alias(libs.plugins.klibs.gradle.detekt) apply false
    alias(libs.plugins.klibs.gradle.detekt.compose) apply false
    alias(libs.plugins.klibs.gradle.dokka.root) apply false
    alias(libs.plugins.klibs.gradle.dokka.module) apply false
    alias(libs.plugins.klibs.gradle.java.core) apply false
    alias(libs.plugins.klibs.gradle.stub.javadoc) apply false
    alias(libs.plugins.klibs.gradle.publication) apply false
    alias(libs.plugins.klibs.gradle.rootinfo) apply false
    alias(libs.plugins.klibs.gradle.android.core) apply false
}

// Apply dokka root and detekt for all project

apply(plugin = "ru.astrainteractive.gradleplugin.detekt")

// For subprojects apply dokkaModule, pulication, infor and java.core if module have kotlin.jvm
subprojects.forEach {
    it.apply(plugin = "ru.astrainteractive.gradleplugin.dokka.root")
    it.apply(plugin = "ru.astrainteractive.gradleplugin.dokka.module")
    it.apply(plugin = "ru.astrainteractive.gradleplugin.publication")
    it.beforeEvaluate {
        apply(plugin = "ru.astrainteractive.gradleplugin.root.info")
    }
    it.plugins.withId("org.jetbrains.kotlin.jvm") {
        it.apply(plugin = "ru.astrainteractive.gradleplugin.java.core")
    }
}

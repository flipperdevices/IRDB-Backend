import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.hierarchyGroup

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    kotlin("plugin.serialization")
    alias(libs.plugins.gradle.android.library)
    alias(libs.plugins.klibs.gradle.android.core)
}

kotlin {
    jvm()
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Serialization
            implementation(libs.kotlin.serialization.json)
        }
    }
}

android.namespace = hierarchyGroup

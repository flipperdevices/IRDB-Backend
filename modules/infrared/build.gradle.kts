plugins {
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization.json)
    // Local
    implementation(projects.modules.buildKonfig)
}

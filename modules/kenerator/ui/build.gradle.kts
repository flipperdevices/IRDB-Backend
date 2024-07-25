plugins {
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization.json)
    implementation(kotlin("test"))
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.database)
    implementation(projects.modules.core)
    implementation(projects.modules.model)
    implementation(projects.modules.database)
    implementation(projects.modules.infrared)
    implementation(projects.modules.kenerator.configuration)
}

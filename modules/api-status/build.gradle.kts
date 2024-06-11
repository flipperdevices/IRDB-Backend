plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.coreModels)
    implementation(projects.modules.core)
}

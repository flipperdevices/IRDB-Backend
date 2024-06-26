plugins {
    id("org.jetbrains.kotlin.jvm")
    application
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
    implementation(projects.modules.apiStatus)
    implementation(projects.modules.core)
    implementation(projects.modules.sharedBackendModel)
    implementation(projects.modules.sharedUiModel)
    implementation(projects.modules.apiStatus)
}

application {
    mainClass.set("com.flipperdevices.ifrmvp.parser.MainKt")
}

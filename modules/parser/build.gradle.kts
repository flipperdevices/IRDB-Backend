plugins {
    id("org.jetbrains.kotlin.jvm")
    application
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization.json)
    implementation(kotlin("test"))
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.apiStatus)
    implementation(projects.modules.core)
    implementation(projects.modules.coreModels)
    implementation(projects.modules.apiStatus)
}

application {
    mainClass.set("com.flipperdevices.ifrmvp.parser.MainKt")
}

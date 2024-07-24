import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlin.serialization.json)
    implementation(kotlin("test"))
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
    implementation(projects.modules.model)
    implementation(projects.modules.infrared)
}

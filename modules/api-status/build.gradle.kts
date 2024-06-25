plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation("com.h2database:h2:2.2.224")
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.sharedBackendModel)
    implementation(projects.modules.sharedUiModel)
    implementation(projects.modules.core)
}

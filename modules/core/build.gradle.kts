plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // Serialization
    implementation(libs.kotlin.serialization.json)
    // Ktor
    libs.versions.ktor.core.get().let { ktor ->
        implementation("io.ktor:ktor-server-core:$ktor")
    }
    // Log
    implementation(libs.logback.classic)
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.sql.driver.mysql)
    // Services
    implementation(projects.modules.buildKonfig)
}

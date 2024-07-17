pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        google()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "backend"

// database
include(":modules:api-status")
// core
include(":modules:build-konfig")
include(":modules:core")
include(":modules:model")
include(":modules:infrared")
// generators
include(":modules:kenerator:configuration")
include(":modules:kenerator:sql")
include(":modules:kenerator:ui")
// application
include("web-api")

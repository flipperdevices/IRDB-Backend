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

// Services
include(":modules:core-models")
include(":modules:core")
include(":modules:api-status")
include(":modules:build-konfig")
include(":modules:parser")
// Master
include("web-api")

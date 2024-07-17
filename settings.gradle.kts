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
include(":modules:core")
include(":modules:api-status")
include(":modules:build-konfig")
include(":modules:parser")
include(":modules:ui-generator")
include(":modules:config-generator")
include(":modules:model")
include(":modules:infrared")
// Master
include("web-api")

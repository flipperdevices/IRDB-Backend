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
include(":modules:database")
// core
include(":modules:build-konfig")
include(":modules:core")
include(":modules:model")
include(":modules:infrared")
include(":modules:resources")
// generators
include(":modules:kenerator:configuration")
include(":modules:kenerator:sql")
include(":modules:kenerator:ui")
include(":modules:kenerator:paths")
// application
include("web-api")

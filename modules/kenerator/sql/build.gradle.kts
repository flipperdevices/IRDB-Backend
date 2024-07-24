import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    application
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gradle.shadow)
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
    implementation(projects.modules.database)
    implementation(projects.modules.core)
    implementation(projects.modules.model)
    implementation(projects.modules.database)
    implementation(projects.modules.infrared)
    implementation(projects.modules.kenerator.paths)
}

application {
    mainClass.set("com.flipperdevices.ifrmvp.parser.MainKt")
}

tasks.shadowJar {
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    from(project.configurations.runtimeClasspath)

    archiveVersion.set(requireProjectInfo.versionString)
    archiveBaseName.set("${requireProjectInfo.name}-parser")
    File(rootDir, "jars").also(File::mkdirs).also(destinationDirectory::set)
}

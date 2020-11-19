import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    maven
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.github.johnrengelman.shadow")
}

description = "FWEchelon"

dependencies {
    compileOnly(Libs.paper)
    compileOnly(Libs.MCCoroutine.core)
    compileOnly(Libs.MCCoroutine.api)
    compileOnly(Libs.coroutines)
    compileOnly(Libs.serialization)
    compileOnly(Libs.koHttp)
    implementation(project(":fwechelon-api"))
}

tasks.withType<ShadowJar> {
    exclude { it.path.startsWith("kotlin") || it.path.startsWith("org") }
}

defaultTasks("build", "shadowJar")
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // java
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.github.johnrengelman.shadow")
}

description = "FWEchelon"
java.sourceCompatibility = JavaVersion.VERSION_1_8

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
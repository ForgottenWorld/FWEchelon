import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
}

description = "FWEchelon"

dependencies {
    compileOnly(Libs.spigot)
    compileOnly(Libs.MCCoroutine.core)
    compileOnly(Libs.MCCoroutine.api)
    compileOnly(Libs.coroutines)
    compileOnly(Libs.serialization)
    compileOnly(Libs.koHttp)
    implementation(Libs.koin)
    implementation(project(":fwechelon-api"))

    testImplementation(Libs.junit)
    testImplementation(Libs.mockBukkit)
    testImplementation(Libs.MCCoroutine.core)
    testImplementation(Libs.MCCoroutine.api)
    testImplementation(Libs.coroutines)
    testImplementation(Libs.serialization)
    testImplementation(Libs.koHttp)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"

tasks.withType<Jar> {
    from(project(":fwechelon-api").sourceSets["main"].output)
}
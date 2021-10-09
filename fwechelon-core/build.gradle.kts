import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

description = "FWEchelon"

dependencies {
    compileOnly(Libs.spigot)
    compileOnly(Libs.skedule)
    compileOnly(Libs.coroutines)
    compileOnly(Libs.serialization)
    compileOnly(Libs.koHttp)
    implementation(Libs.koin)
    implementation(project(":fwechelon-api"))

    testImplementation(Libs.spigot)
    testImplementation(Libs.skedule)
    testImplementation(Libs.coroutines)
    testImplementation(Libs.serialization)
    testImplementation(Libs.koHttp)
    testImplementation(Libs.mockBukkit)
    testImplementation(Libs.junit)
}

tasks.withType<ShadowJar> {
    val kept = setOf("it.forgottenworld", "io.insert-koin")
    dependencies {
        exclude {
            !kept.contains(it.moduleGroup)
        }
    }
    relocate("org.koin", "it.forgottenworld.echelon.koin")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.withType<Jar> {
    from(project(":fwechelon-api").sourceSets["main"].output)
}

tasks.register("localDeploy") {
    doLast {
        copy {
            from("build/libs")
            into("/home/giacomo/paper/plugins")
            include("**/*-all.jar")
        }
    }
}
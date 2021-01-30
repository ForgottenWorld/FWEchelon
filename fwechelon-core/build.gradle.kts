import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.github.johnrengelman.shadow") version "6.1.0"
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
}

tasks.withType<ShadowJar> {
    dependencies {
        exclude {
            !setOf("it.forgottenworld", "org.koin").contains(it.moduleGroup)
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
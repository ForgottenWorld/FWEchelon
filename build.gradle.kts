import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":fwechelon-core"))
    implementation(project(":fwechelon-api"))
}

plugins {
    `maven-publish`
    kotlin("jvm") version Versions.kotlin
    id("com.github.ben-manes.versions") version "0.39.0"
}

subprojects {

    group = "it.forgottenworld"
    version = Versions.echelon

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "16"
        targetCompatibility = "16"
        options.encoding = "UTF-8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "16"
        }
    }

}
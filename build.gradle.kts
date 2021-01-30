import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(project(":fwechelon-core"))
    implementation(project(":fwechelon-api"))
}

plugins {
    maven
    kotlin("jvm") version Versions.kotlin
}

subprojects {

    group = "it.forgottenworld"
    version = Versions.echelon

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "UTF-8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

}
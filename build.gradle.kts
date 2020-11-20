import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
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
    id(Libs.Plugins.shadow) version Versions.shadow
}

subprojects {

    group = "it.forgottenworld"
    version = Versions.echelon

    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://papermc.io/repo/repository/maven-public/")
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

tasks.withType<ShadowJar> {
    exclude { it.path.startsWith("kotlin") || it.path.startsWith("org") }
}

tasks["build"].dependsOn("shadowJar")
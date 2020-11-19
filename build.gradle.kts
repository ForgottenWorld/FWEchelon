import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    // java
    // `maven-publish`
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

/*publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}*/
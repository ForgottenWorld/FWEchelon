plugins {
    `maven-publish`
    kotlin("jvm")
    java
}

description = "FWEchelonAPI"
java.sourceCompatibility = JavaVersion.VERSION_16

dependencies {
    compileOnly(Libs.spigot)
}
plugins {
    maven
    kotlin("jvm")
    java
}

description = "FWEchelonAPI"
java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {
    compileOnly(Libs.spigot)
}
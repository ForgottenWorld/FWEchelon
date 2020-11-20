plugins {
    maven
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
}

description = "FWEchelon"

dependencies {
    compileOnly(Libs.paper)
    compileOnly(Libs.MCCoroutine.core)
    compileOnly(Libs.MCCoroutine.api)
    compileOnly(Libs.coroutines)
    compileOnly(Libs.serialization)
    compileOnly(Libs.koHttp)
    implementation(project(":fwechelon-api"))
}

tasks.withType<Jar> {
    from(project(":fwechelon-api").sourceSets["main"].output)
}
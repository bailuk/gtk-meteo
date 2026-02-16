import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    application

    // https://gradleup.com/shadow/getting-started/
    id("com.gradleup.shadow") version "9.3.1"

    // https://kotlinlang.org/docs/gradle-configure-project.html
    kotlin("jvm") version "2.3.10"
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    gradlePluginPortal()
    maven { url = uri("https://jitpack.io") }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.test {
    useJUnitPlatform()
}

val mapsForgeVersion: String by project
val mapsForgeGtkVersion: String by project

dependencies {
    implementation("org.mapsforge:mapsforge-map-reader:${mapsForgeVersion}")
    implementation("com.github.bailuk:mapsforge-gtk:${mapsForgeGtkVersion}")

    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.13.2")

    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:5.2.3")
}


val appMainClass = "AppKt"

application {
    mainClass.set(appMainClass)
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to appMainClass))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

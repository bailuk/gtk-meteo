plugins {
    application

    // https://imperceptiblethoughts.com/shadow/getting-started
    id("com.github.johnrengelman.shadow") version "8.1.1"

    // https://kotlinlang.org/docs/gradle-configure-project.html
    kotlin("jvm") version "2.0.10"
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("maven-local") }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
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
    implementation("com.google.code.gson:gson:2.10.1")

    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
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

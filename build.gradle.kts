plugins {
    application

    // https://imperceptiblethoughts.com/shadow/getting-started
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // https://kotlinlang.org/docs/gradle.html#targeting-the-jvm
    kotlin("jvm") version "1.7.10"
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    maven { url = uri("https://jitpack.io") }
    mavenCentral()
    mavenLocal()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
}


dependencies {
    implementation("org.mapsforge:mapsforge-map-reader:0.15.0")
    implementation("org.mapsforge:mapsforge-map-gtk:SNAPSHOT")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
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

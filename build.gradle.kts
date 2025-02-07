plugins {
    kotlin("jvm") version "2.1.0"  // Kotlin plugin
    id("application")  // Correct way to add the application plugin
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()  // Required for dependencies
}

dependencies {
    implementation(kotlin("stdlib"))  // Kotlin Standard Library (needed for Kotlin code)
    testImplementation(kotlin("test"))  // Test support
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)  // Using Java 21 as the toolchain
}

application {
    mainClass.set("MainKt")  // Set the main entry point for the application
}


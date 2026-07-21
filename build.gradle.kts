plugins {
    kotlin("jvm") version "2.2.21"
}

group = "bytebloom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(8)
}

tasks.test {
    useJUnitPlatform()
}
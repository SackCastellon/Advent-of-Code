plugins {
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

    test {
        useJUnitPlatform()
    }
}

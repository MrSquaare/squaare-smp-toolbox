pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("net.fabricmc.fabric-loom-remap") version providers.gradleProperty("loom_version")
        id("org.jlleitschuh.gradle.ktlint") version providers.gradleProperty("ktlint_gradle_version")
        id("io.gitlab.arturbosch.detekt") version providers.gradleProperty("detekt_gradle_version")
    }
}

// Should match your modid
rootProject.name = "squaare-smp-toolbox"

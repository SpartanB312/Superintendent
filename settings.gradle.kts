rootProject.name = "Superintendent"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://files.minecraftforge.net/maven")
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }

    val kotlinVersion: String by settings
    val forgeGradleVersion: String by settings
    val mixinGradleVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("net.minecraftforge.gradle") version forgeGradleVersion
        id("org.spongepowered.mixin") version mixinGradleVersion
    }

    resolutionStrategy.eachPlugin {
        if (requested.id.id == "net.minecraftforge.gradle.ForgeGradle") {
            useModule("net.minecraftforge.gradle:ForgeGradle:${requested.version}")
        }
    }
}

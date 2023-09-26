import net.minecraftforge.gradle.userdev.UserDevExtension
import org.spongepowered.asm.gradle.plugins.MixinExtension

val modGroup: String by project
val modVersion: String by project

group = modGroup
version = modVersion

plugins {
    java
    kotlin("jvm")
    id("net.minecraftforge.gradle")
    id("org.spongepowered.mixin")
}

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public/") // Alibaba maven mirror
    mavenCentral()
    maven("https://repo1.maven.org/maven2/")
    maven("https://repo.spongepowered.org/repository/maven-public/")
    maven("https://mvnrepository.com/artifact/")
    maven("https://jitpack.io/")
}

val kotlinxCoroutineVersion: String by project
val minecraftVersion: String by project
val forgeVersion: String by project
val mappingsChannel: String by project
val mappingsVersion: String by project

val jarLibImplementation: Configuration by configurations.creating

val jarLib: Configuration by configurations.creating {
    extendsFrom(jarLibImplementation)
}

configurations.implementation {
    extendsFrom(jarLibImplementation)
}

afterEvaluate {
    configurations["minecraft"].resolvedConfiguration.resolvedArtifacts.forEach {
        val id = it.moduleVersion.id
        jarLib.exclude(id.group, it.name)
    }
}

dependencies {
    "minecraft"("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")
    jarLibImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    jarLibImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    jarLibImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    jarLibImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion")
    jarLibImplementation("org.spongepowered:mixin:0.8-SNAPSHOT") {
        exclude("commons-io")
        exclude("gson")
        exclude("guava")
        exclude("launchwrapper")
        exclude("log4j-core")
    }
    jarLibImplementation("org.joml:joml:1.10.5")
    annotationProcessor("org.spongepowered:mixin:0.8.3:processor") {
        exclude("gson")
    }
}

configure<UserDevExtension> {
    mappings(mappingsChannel, mappingsVersion)

    runs {
        create("client") {
            workingDirectory = project.file("run").path
            properties(
                mapOf(
                    "forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP",
                    "forge.logging.console.level" to "info",
                    "fml.coreMods.load" to "net.spartanb312.superintendent.launch.FMLCoreMod",
                    "mixin.env.disableRefMap" to "true"
                )
            )
        }
    }
}

configure<MixinExtension> {
    add(sourceSets["main"], "mixins.superintendent.refmap.json")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlin.contracts.ExperimentalContracts",
                "-Xlambdas=indy",
                "-Xjvm-default=all",
                "-Xbackend-threads=0",
                "-Xcontext-receivers"
            )
        }
    }

    jar {
        manifest {
            attributes(
                "Manifest-Version" to 1.0,
                "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
                "FMLCorePlugin" to "net.spartanb312.superintendent.launch.FMLCoreMod",
                "FMLCorePluginContainsFMLMod" to "true",
                "ForceLoadAsMod" to "true"
            )
        }
        from(
            jarLib.elements.map { set ->
                set.map { fileSystemLocation ->
                    fileSystemLocation.asFile.let {
                        if (it.isDirectory) it else zipTree(it)
                    }
                }
            }
        )
        exclude(
            "META-INF/versions/**",
            "**/*.RSA",
            "**/*.SF",
            "**/module-info.class",
            "**/LICENSE",
            "**/*.txt"
        )
    }
}
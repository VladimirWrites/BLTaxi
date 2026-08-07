// Top-level build file where you can add configuration options common to all sub-projects/modules.

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.compose.compiler.plugin)
        classpath(libs.kotlin.serialization.plugin)
        classpath(libs.ksp.gradle.plugin)
        classpath(libs.google.services.plugin)
        classpath(libs.firebase.crashlytics.plugin)
        classpath(libs.hilt.gradle.plugin)
        classpath(libs.gradle.versions.plugin)
    }
}

apply(plugin = "com.github.ben-manes.versions")

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        google()
    }
}

// Shared Android configuration. Replaces the old buildsystem/java_version.gradle script plugin,
// which used the legacy DSL that AGP 9 no longer exposes.
val compileSdkVersion = libs.versions.compileSdk.get().toInt()
val minSdkVersion = libs.versions.minSdk.get().toInt()
val targetSdkVersion = libs.versions.targetSdk.get().toInt()

subprojects {
    pluginManager.withPlugin("com.android.library") {
        extensions.configure<LibraryExtension> {
            compileSdk = compileSdkVersion
            defaultConfig {
                minSdk = minSdkVersion
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }

    pluginManager.withPlugin("com.android.application") {
        extensions.configure<ApplicationExtension> {
            compileSdk = compileSdkVersion
            defaultConfig {
                minSdk = minSdkVersion
                targetSdk = targetSdkVersion
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

// Configure Gradle Versions Plugin to reject unstable versions
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { keyword: String ->
            candidate.version.uppercase().contains(keyword)
        }
        val unstableKeyword = listOf("ALPHA", "BETA", "RC", "DEV", "SNAPSHOT").any { keyword: String ->
            candidate.version.uppercase().contains(keyword)
        }
        !stableKeyword && unstableKeyword
    }

    // Check for updates from release versions only
    checkForGradleUpdate = true
    outputFormatter = "plain,html"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}

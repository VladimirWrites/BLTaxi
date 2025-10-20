// Top-level build file where you can add configuration options common to all sub-projects/modules.

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

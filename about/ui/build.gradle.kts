plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

apply(from = "$rootDir/buildsystem/java_version.gradle")

android {
    namespace = "com.vlad1m1r.bltaxi.about.ui"

    buildTypes {
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false
        }
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        animationsDisabled = true
        unitTests.all {
            it.systemProperty("robolectric.dependency.repo.url", "https://repo1.maven.org/maven2")
        }
    }
}

dependencies {
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.appcompat)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    debugImplementation(libs.compose.ui.tooling)

    // Compose integration
    implementation(libs.compose.activity)
    implementation(libs.compose.lifecycle.viewmodel)
    implementation(libs.hilt.navigation.compose)

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    implementation(project(":baseui"))
    implementation(project(":about:domain"))
    implementation(project(":basedata"))

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.robolectric)
    testImplementation(libs.arch.core.testing)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
}

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

apply(from = "../buildsystem/java_version.gradle")

android {
    namespace = "com.vlad1m1r.baseui"

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
}

dependencies {
    implementation(libs.kotlin.coroutines.core)

    api(libs.material.design)
    api(libs.cardview)
    api(libs.appcompat)
    api(libs.core.ktx)

    // Compose
    api(platform(libs.compose.bom))
    api(libs.compose.material)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.runtime)
    api(libs.compose.material.icons.core)
    api(libs.compose.material.icons.extended)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
}

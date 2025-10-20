plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/buildsystem/java_version.gradle")

android {
    namespace = "com.vlad1m1r.bltaxi.taxi.data"

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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(project(":taxi:domain"))
    implementation(project(":local"))
    implementation(project(":remote"))

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
}

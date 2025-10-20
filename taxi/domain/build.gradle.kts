plugins {
    id("com.android.library")
    id("kotlin-android")
}

apply(from = "$rootDir/buildsystem/java_version.gradle")

android {
    namespace = "com.vlad1m1r.bltaxi.taxi.domain"

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
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.appcompat)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
}

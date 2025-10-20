plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

apply(from = "../buildsystem/java_version.gradle")

android {
    namespace = "com.vlad1m1r.bltaxi.sync"

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
    implementation(libs.work.manager)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    api(libs.hilt.work)
    ksp(libs.hilt.android.compiler)

    implementation(project(":remote"))
    implementation(project(":local"))
    implementation(project(":taxi:domain"))

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.robolectric)
    testImplementation(libs.work.manager.test)
}

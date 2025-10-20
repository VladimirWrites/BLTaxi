plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

apply(from = "../buildsystem/java_version.gradle")

android {
    namespace = "com.vlad1m1r.bltaxi.shortcuts"

    defaultConfig {
        testOptions.unitTests.isIncludeAndroidResources = true
    }

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

    testOptions {
        unitTests.all {
            it.systemProperty("robolectric.dependency.repo.url", "https://repo1.maven.org/maven2")
        }
    }
}

dependencies {
    implementation(libs.appcompat)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(project(":taxi:domain"))

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.robolectric)
    testImplementation(libs.arch.core.testing)
}

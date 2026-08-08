plugins {
    id("com.android.library")
}


android {
    namespace = "com.vlad1m1r.bltaxi.about.domain"

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

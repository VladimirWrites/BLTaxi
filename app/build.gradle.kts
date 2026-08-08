plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("androidx.baselineprofile")
}


android {
    namespace = "com.vlad1m1r.bltaxi"

    defaultConfig {
        applicationId = "com.vlad1m1r.bltaxi"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false
        }
    }

    // The baseline profile plugin derives nonMinifiedRelease and benchmarkRelease from release.
    // Release itself is unsigned here, so those would build but fail to install on the device
    // that has to run the generator. They are throwaway measurement builds, so the debug key is
    // the right one — and this deliberately does not touch the real release variant.
    buildTypes.configureEach {
        if (name == "nonMinifiedRelease" || name == "benchmarkRelease") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    testOptions {
        animationsDisabled = true
        unitTests.all {
            it.systemProperty("robolectric.dependency.repo.url", "https://repo1.maven.org/maven2")
        }
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

base {
    archivesName = "BL Taxi-${libs.versions.versionName.get()}"
}

dependencies {
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.preference.ktx)
    implementation(libs.startup.runtime)
    implementation(libs.profileinstaller)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Compose dependencies for navigation
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.compose.navigation)
    implementation(libs.compose.activity)
    debugImplementation(libs.compose.ui.tooling)

    implementation(project(":taxi:domain"))
    implementation(project(":basedata"))
    implementation(project(":baseui"))
    implementation(project(":about:data"))
    implementation(project(":about:ui"))
    implementation(project(":taxi:data"))
    implementation(project(":taxi:ui"))
    implementation(project(":settings:ui"))
    implementation(project(":remote"))
    implementation(project(":local"))
    implementation(project(":analytics"))
    implementation(project(":sync"))
    implementation(project(":shortcuts"))

    testImplementation(libs.junit)

    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.robolectric)
    testImplementation(libs.espresso.core)
    testImplementation(libs.espresso.contrib)
    testImplementation(libs.test.core.ktx)
    testImplementation(libs.hilt.test)
    kspTest(libs.hilt.compiler)

    // Compose test dependencies
    testImplementation(libs.compose.ui.test.junit4)
    testImplementation(libs.navigation.testing)

    // Supplies the generated profile to release builds.
    baselineProfile(project(":baselineprofile"))
}

apply(plugin = "com.google.gms.google-services")

hilt {
    enableAggregatingTask = true
}

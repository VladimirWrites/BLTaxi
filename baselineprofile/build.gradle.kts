plugins {
    id("com.android.test")
    id("androidx.baselineprofile")
}

android {
    namespace = "com.vlad1m1r.bltaxi.baselineprofile"

    // Set here rather than inherited: the shared configuration in the root build file only
    // covers the library and application plugins, not com.android.test.
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        // UiAutomator and the macrobenchmark runner need API 28+ to collect a profile.
        minSdk = 28
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"
}

// The generator drives the real app, so it needs a device. Physical hardware gives more
// representative results than an emulator; requireUnlockedDevice keeps a locked screen from
// silently producing a useless profile.
baselineProfile {
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.uiautomator)
    implementation(libs.benchmark.macro.junit4)
}

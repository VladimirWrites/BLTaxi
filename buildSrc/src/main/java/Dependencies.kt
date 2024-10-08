object Versions {
    const val kotlin = "1.9.20"
    const val kotlin_coroutines = "1.7.3"

    const val android_x_legacy_v4 = "1.0.0"
    const val android_x_fragment = "1.6.1"
    const val android_x_app_compat = "1.6.1"
    const val android_x_core_ktx = "1.10.1"
    const val android_x_card_view = "1.0.0"
    const val android_x_recycler_view = "1.3.1"
    const val android_x_lifecycle = "2.6.1"
    const val android_x_constraint_layout = "2.1.4"
    const val material_design = "1.9.0"
    const val android_x_preference = "1.2.0"
    const val android_x_work_manager = "2.8.1"
    const val android_x_databinding_compiler = "8.1.0"

    const val android_x_navigation = "2.6.0"

    const val room = "2.4.2"

    const val firebase_core = "21.1.1"
    const val firebase_analytics = "21.3.0"
    const val firebase_crashlytics = "18.4.0"

    const val hilt = "2.47"
    const val hiltAndroid = "1.0.0"

    const val retrofit = "2.9.0"
    const val ok_http_interceptor = "4.11.0"

    const val junit = "4.13.2"
    const val mockito = "4.5.1"
    const val mockito_kotlin = "2.2.0"
    const val truth = "1.1.3"
    const val robolectric = "4.7.3"
    const val arch_core_testing = "2.1.0"
    const val fragment_test = "1.4.1"
    const val espresso_core = "3.4.0"
    const val test_core_ktx = "1.4.0"

    const val gradle_android = "8.2.1"
    const val google_services = "4.3.15"
    const val crashlytics_gradle_plugin = "2.8.1"

    const val min_sdk = 21
    const val target_sdk = 33
    const val compile_sdk = 33

    const val version_code = 20311
    const val version_name = "2.3.1"
}

object Deps {

    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_android}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val google_services_plugin = "com.google.gms:google-services:${Versions.google_services}"
    const val crashlytics_gradle_plugin = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics_gradle_plugin}"
    const val hilt_android_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object Modules {
    const val about_ui= ":about:ui"
    const val about_domain = ":about:domain"
    const val about_data = ":about:data"
    const val analytics = ":analytics"
    const val basedata = ":basedata"
    const val baseui = ":baseui"
    const val remote = ":remote"
    const val local = ":local"
    const val taxi_data = ":taxi:data"
    const val taxi_domain = ":taxi:domain"
    const val taxi_ui = ":taxi:ui"
    const val settings_ui = ":settings:ui"
    const val sync = ":sync"
    const val shortcuts = ":shortcuts"
}

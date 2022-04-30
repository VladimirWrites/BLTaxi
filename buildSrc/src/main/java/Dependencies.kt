object Versions {
    const val kotlin = "1.6.21"
    const val kotlin_coroutines = "1.6.1"

    const val android_x_legacy_v4 = "1.0.0"
    const val android_x_fragment = "1.4.1"
    const val android_x_app_compat = "1.4.1"
    const val android_x_core_ktx = "1.7.0"
    const val android_x_card_view = "1.0.0"
    const val android_x_recycler_view = "1.2.0"
    const val android_x_lifecycle = "2.4.1"
    const val android_x_constraint_layout = "2.1.3"
    const val material_design = "1.5.0"
    const val android_x_preference = "1.2.0"
    const val android_x_work_manager = "2.7.1"
    const val android_x_databinding_compiler = "7.1.3"

    const val android_x_navigation = "2.4.2"

    const val room = "2.4.2"

    const val firebase_core = "20.1.2"
    const val firebase_analytics = "20.1.2"
    const val firebase_crashlytics = "18.2.9"

    const val hilt = "2.41"
    const val hiltAndroid = "1.0.0"

    const val retrofit = "2.9.0"
    const val ok_http_interceptor = "4.9.3"

    const val junit = "4.13.2"
    const val mockito = "4.5.1"
    const val mockito_kotlin = "2.2.0"
    const val truth = "1.1.3"
    const val robolectric = "4.7.3"
    const val arch_core_testing = "2.1.0"
    const val fragment_test = "1.4.1"
    const val espresso_core = "3.4.0"
    const val test_core_ktx = "1.4.0"

    const val gradle_android = "7.1.3"
    const val google_services = "4.3.10"
    const val crashlytics_gradle_plugin = "2.8.1"

    const val min_sdk = 21
    const val target_sdk = 31
    const val compile_sdk = 31

    const val version_code = 20211
    const val version_name = "2.2.1"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"

    const val support = "androidx.legacy:legacy-support-v4:${Versions.android_x_legacy_v4}"
    const val fragment = "androidx.fragment:fragment:${Versions.android_x_fragment}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.android_x_app_compat}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.android_x_core_ktx}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.android_x_recycler_view}"
    const val material_design = "com.google.android.material:material:${Versions.material_design}"
    const val cardview = "androidx.cardview:cardview:${Versions.android_x_card_view}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.android_x_constraint_layout}"
    const val preference_ktx = "androidx.preference:preference-ktx:${Versions.android_x_preference}"
    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.android_x_work_manager}"
    const val databinding_compiler = "androidx.databinding:databinding-compiler:${Versions.android_x_databinding_compiler}"

    const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.android_x_lifecycle}"

    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.android_x_navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.android_x_navigation}"

    const val firebase_core = "com.google.firebase:firebase-core:${Versions.firebase_core}"
    const val firebase_analytics = "com.google.firebase:firebase-analytics:${Versions.firebase_analytics}"
    const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.firebase_crashlytics}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_test = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hilt_work = "androidx.hilt:hilt-work:${Versions.hiltAndroid}"
    const val hilt_android_compiler = "androidx.hilt:hilt-compiler:${Versions.hiltAndroid}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val ok_http_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.ok_http_interceptor}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_common = "androidx.room:room-common:${Versions.room}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val arch_core_testing = "androidx.arch.core:core-testing:${Versions.arch_core_testing}"
    const val fragment_test = "androidx.fragment:fragment-testing:${Versions.fragment_test}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso_core}"
    const val test_core_ktx = "androidx.test:core-ktx:${Versions.test_core_ktx}"
    const val work_manager_test = "androidx.work:work-testing:${Versions.android_x_work_manager}"

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

package com.vlad1m1r.bltaxi

import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.about.AppInfoProvider
import org.junit.Test

class AppInfoProviderTest {

    val appInfoProvider: AppInfoProvider = AppInfoProviderImpl()

    @Test
    fun getApplicationId() {
        assertThat(appInfoProvider.getApplicationId()).isEqualTo(BuildConfig.APPLICATION_ID)
    }

    @Test
    fun getVersionName() {
        assertThat(appInfoProvider.getVersionName()).isEqualTo(BuildConfig.VERSION_NAME)
    }
}

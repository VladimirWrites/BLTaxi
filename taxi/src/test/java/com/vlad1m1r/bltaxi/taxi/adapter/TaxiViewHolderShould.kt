package com.vlad1m1r.bltaxi.taxi.adapter

import android.app.Application
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.taxi.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_App)
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = TestApplication::class)
class TaxiViewHolderShould {

    @Test
    fun createBinding() {
        val view: View = LayoutInflater
            .from(InstrumentationRegistry.getInstrumentation().targetContext)
            .inflate(
                R.layout.item_taxi,
                null,
                true
            )

        val taxiViewHolder = TaxiViewHolder(view)

        assertThat(taxiViewHolder.binding).isNotNull()
    }
}
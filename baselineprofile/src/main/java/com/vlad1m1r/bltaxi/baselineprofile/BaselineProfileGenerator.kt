package com.vlad1m1r.bltaxi.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val PACKAGE_NAME = "com.vlad1m1r.bltaxi"
private const val LIST_TAG = "taxiList"
private const val LIST_TIMEOUT_MS = 15_000L

/**
 * Records the classes and methods used on the way to the first usable screen, so ART can compile
 * them ahead of time instead of interpreting them on a cold start.
 *
 * Run it against a connected device:
 *
 *     ./gradlew :app:generateReleaseBaselineProfile
 *
 * The result is written to app/src/release/generated/baselineProfiles/ and is picked up
 * automatically by release builds. Regenerate it after any significant UI change.
 */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startupAndBrowseTaxis() = baselineProfileRule.collect(
        packageName = PACKAGE_NAME,
        // Also emit a startup profile, which ART uses to lay out the dex for faster class loading.
        includeInStartupProfile = true
    ) {
        pressHome()
        startActivityAndWait()

        // The list only appears once the taxis have been read from Room, so everything up to
        // here covers Hilt graph creation, the theme and the first composition.
        device.wait(Until.hasObject(By.res(LIST_TAG)), LIST_TIMEOUT_MS)

        val list = device.findObject(By.res(LIST_TAG))
        if (list != null) {
            // Keep the gesture away from the system back gesture area at the screen edges.
            list.setGestureMargin(device.displayWidth / 5)

            // Scrolling compiles the lazy layout and card composition paths.
            list.fling(Direction.DOWN)
            device.waitForIdle()
            list.fling(Direction.UP)
            device.waitForIdle()

            // Expanding a card pulls in the tariff table, the discount note and the button group,
            // none of which are composed until first opened.
            list.children.firstOrNull()?.click()
            device.waitForIdle()
        }
    }
}

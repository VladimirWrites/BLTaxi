package com.vlad1m1r.bltaxi.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
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
 * Records the classes and methods the app uses on the way to a usable screen, so ART can compile
 * them ahead of time instead of interpreting them on a cold start.
 *
 * Run against a connected device:
 *
 *     ./gradlew :app:generateReleaseBaselineProfile
 *
 * Output lands in app/src/release/generated/baselineProfiles/ and release builds pick it up
 * automatically. Regenerate after significant UI changes.
 *
 * Deliberately split in two. Both tests feed the baseline profile, but only [startup] feeds the
 * startup profile, which ART uses to decide the dex layout — which classes to pack together for
 * fast loading at launch. Letting the scrolling journey into that would dilute it with classes
 * that are not needed until the user interacts.
 */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    /** Cold start only: Hilt graph, theme, first composition, first frame. */
    @Test
    fun startup() = baselineProfileRule.collect(
        packageName = PACKAGE_NAME,
        includeInStartupProfile = true
    ) {
        pressHome()
        startActivityAndWait()
        awaitTaxiList()
    }

    /** What the user does next: scroll the list and open a card. */
    @Test
    fun browseTaxis() = baselineProfileRule.collect(
        packageName = PACKAGE_NAME,
        includeInStartupProfile = false
    ) {
        pressHome()
        startActivityAndWait()

        val list = awaitTaxiList() ?: return@collect

        // Keep the gesture clear of the system back-gesture area at the screen edges.
        list.setGestureMargin(device.displayWidth / 5)

        // Scrolling compiles the lazy layout and card composition paths.
        list.fling(Direction.DOWN)
        device.waitForIdle()
        list.fling(Direction.UP)
        device.waitForIdle()

        // Expanding pulls in the tariff table, the discount note and the button group, none of
        // which are composed until a card is first opened.
        list.children.firstOrNull()?.click()
        device.waitForIdle()
    }

    /**
     * Waits for the taxi list, which only appears once the taxis have been read from Room.
     * Returns null if it never shows, so a failed lookup produces a startup-only profile rather
     * than a crash.
     */
    private fun MacrobenchmarkScope.awaitTaxiList() = device
        .wait(Until.hasObject(By.res(LIST_TAG)), LIST_TIMEOUT_MS)
        .let { device.findObject(By.res(LIST_TAG)) }
}

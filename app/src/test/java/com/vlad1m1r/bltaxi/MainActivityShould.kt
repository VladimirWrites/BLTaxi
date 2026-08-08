package com.vlad1m1r.bltaxi

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [29],
    application = HiltTestApplication::class,
    qualifiers = "en"
)
class MainActivityShould {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun launchSuccessfully() {
        // Activity launches without crashing - test passes if no exception thrown
        composeTestRule.activity
    }

    @Test
    fun renderComposeContent() {
        // MainActivity uses setContent (Compose), not setContentView (XML): the only way the
        // app bar title can be found as a semantics node is through the Compose hierarchy.
        composeTestRule
            .onNodeWithText("BL Taxi")
            .assertIsDisplayed()
    }
}

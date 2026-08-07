package com.vlad1m1r.bltaxi.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vlad1m1r.bltaxi.MainActivity
import com.vlad1m1r.bltaxi.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests navigation between the Compose destinations, driven through the top app bar
 * exactly as a user would: overflow menu to go forward, up button to come back.
 */
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [29],
    application = HiltTestApplication::class,
    qualifiers = "en"
)
class NavigationFlowShould {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val homeTitle by lazy {
        composeTestRule.activity.getString(com.vlad1m1r.bltaxi.taxi.ui.R.string.app_name)
    }
    private val settingsTitle by lazy {
        composeTestRule.activity.getString(com.vlad1m1r.bltaxi.settings.ui.R.string.settings__name)
    }
    private val aboutTitle by lazy {
        composeTestRule.activity.getString(com.vlad1m1r.bltaxi.about.ui.R.string.about__name)
    }
    private val moreOptions by lazy {
        composeTestRule.activity.getString(R.string.content_description_more_options)
    }
    private val navigateBack by lazy {
        composeTestRule.activity.getString(R.string.content_description_navigate_back)
    }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun openOverflowItem(label: String) {
        composeTestRule.onNodeWithContentDescription(moreOptions).performClick()
        composeTestRule.onNodeWithText(label).performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun startOnTaxiScreen() {
        composeTestRule.onNodeWithText(homeTitle).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(navigateBack).assertDoesNotExist()
    }

    @Test
    fun navigateFromTaxiToSettings() {
        openOverflowItem(settingsTitle)

        composeTestRule.onNodeWithText(settingsTitle).assertIsDisplayed()
    }

    @Test
    fun navigateFromTaxiToAbout() {
        openOverflowItem(aboutTitle)

        composeTestRule.onNodeWithText(aboutTitle).assertIsDisplayed()
    }

    @Test
    fun navigateBackFromSettingsToTaxi() {
        openOverflowItem(settingsTitle)

        composeTestRule.onNodeWithContentDescription(navigateBack).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(homeTitle).assertIsDisplayed()
    }

    @Test
    fun navigateBackFromAboutToTaxi() {
        openOverflowItem(aboutTitle)

        composeTestRule.onNodeWithContentDescription(navigateBack).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(homeTitle).assertIsDisplayed()
    }

    @Test
    fun hideOverflowMenuOnDetailScreens() {
        openOverflowItem(settingsTitle)

        composeTestRule.onNodeWithContentDescription(moreOptions).assertDoesNotExist()
    }
}

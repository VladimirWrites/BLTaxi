package com.vlad1m1r.bltaxi.about

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.vlad1m1r.bltaxi.R

fun aboutScreen(
    func: AboutFragmentRobot.() -> Unit
) = AboutFragmentRobot().apply { func() }

class AboutFragmentRobot {
    fun clickButtonSendEmail() {
        onView(withId(R.id.buttonSendEmail)).perform(scrollTo(), click())
    }

    fun clickButtonRateApp() {
        onView(withId(R.id.buttonRateApp)).perform(scrollTo(), click())
    }

    fun clickButtonShareApp() {
        onView(withId(R.id.buttonShareApp)).perform(scrollTo(), click())
    }

    fun clickButtonPrivacyPolicy() {
        onView(withId(R.id.buttonPrivacyPolicy)).perform(scrollTo(), click())
    }

    fun clickButtonTermsAndConditions() {
        onView(withId(R.id.buttonTermsAndConditions)).perform(scrollTo(), click())
    }

    fun textAppVersionIsEqualTo(text: String) {
        onView(withId(R.id.textAppVersion)).check(matches(withText(text)))
    }
}
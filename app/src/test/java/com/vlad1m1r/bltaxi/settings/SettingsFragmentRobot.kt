package com.vlad1m1r.bltaxi.settings

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*

fun settingsScreen(
    func: SettingsFragmentRobot.() -> Unit
) = SettingsFragmentRobot().apply { func() }

class SettingsFragmentRobot {

    fun clickOnAnalyticsSwitch() {
        clickPreferenceAtPosition(1)
    }

    fun clickOnCrashReportingSwitch() {
        clickPreferenceAtPosition(2)
    }

    private fun clickPreferenceAtPosition(position: Int) {
        onView(withId(androidx.preference.R.id.recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        );
    }
}
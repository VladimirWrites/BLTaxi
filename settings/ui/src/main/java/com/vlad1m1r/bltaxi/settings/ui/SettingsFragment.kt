package com.vlad1m1r.bltaxi.settings.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.vlad1m1r.baseui.addOnPropertyChanged
import dagger.hilt.android.AndroidEntryPoint
import com.vlad1m1r.bltaxi.analytics.R as analyticsR

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.settings__name)
        }

        viewModel.mode.addOnPropertyChanged {
            setDefaultNightMode(it.get())
        }

        val theme = findPreference<ListPreference>(getString(analyticsR.string.pref_key_theme_picker))!!
        theme.setOnPreferenceChangeListener { _, newValue ->
            viewModel.changeTheme(newValue as String)
            true
        }

        findPreference<SwitchPreference>(getString(analyticsR.string.pref_key_analytics))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableTracking(newValue as Boolean)
                true
            }
        }

        findPreference<SwitchPreference>(getString(analyticsR.string.pref_key_crash_reports))!!.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.enableCrashReport(newValue as Boolean)
                true
            }
        }
    }
}

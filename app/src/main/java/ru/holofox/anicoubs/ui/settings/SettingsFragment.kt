package ru.holofox.anicoubs.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.vk.api.sdk.VK
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.features.domain.providers.LocaleManagerProvider
import ru.holofox.anicoubs.internal.enums.UnitSystem
import ru.holofox.anicoubs.ui.LoginActivity

class SettingsFragment : PreferenceFragmentCompat(), KodeinAware {
    override val kodein by closestKodein()

    private val localeManagerProvider: LocaleManagerProvider by instance()

    companion object {

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->

            val stringValue = value.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val index = preference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(if (index >= 0) preference.entries[index] else null)

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        private fun PreferenceFragmentCompat.requirePreference(key: String): Preference {
            return findPreference(key)!!
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateActionBarTitle(getString(R.string.menu_settings))
    }

    private fun updateActionBarTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        /**
         * Bind preference summary to value for lists and sorting list preferences
         */

        bindPreferenceSummaryToValue(requirePreference(getString(R.string.pref_key_name)))
        bindPreferenceSummaryToValue(requirePreference(getString(R.string.pref_key_theme)))
        bindPreferenceSummaryToValue(requirePreference(getString(R.string.pref_key_language)))

        bindLanguagePreference(requirePreference(getString(R.string.pref_key_language)))
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        // Set the listener to watch for value changes.
        preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
            PreferenceManager
                .getDefaultSharedPreferences(preference.context)
                .getString(preference.key, ""))
    }

    private fun bindLanguagePreference(preference: Preference) {
        preference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            localeManagerProvider.setNewLocale(context!!, UnitSystem.valueOf(value.toString()))
            activity?.recreate()
            true
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when (preference.key) {
            getString(R.string.pref_key_logout) -> {
                VK.logout()
                context?.let { LoginActivity.startFrom(it) }
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }
}
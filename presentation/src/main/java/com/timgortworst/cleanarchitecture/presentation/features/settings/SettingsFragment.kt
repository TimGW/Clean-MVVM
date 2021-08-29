package com.timgortworst.cleanarchitecture.presentation.features.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs.Companion.SHARED_PREF_WATCH_PROVIDER_REGION
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.base.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private var darkModePref: ListPreference? = null
    private var themePref: ListPreference? = null
    private var regionsPref: ListPreference? = null

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var themeHelper: ThemeHelper

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        darkModePref = (findPreference("dark_mode_key") as? ListPreference)
        regionsPref = (findPreference(SHARED_PREF_WATCH_PROVIDER_REGION) as? ListPreference)
        themePref = (findPreference("theme_key") as? ListPreference)

        observeWatchProviders()

        displayPrefs()
        regionsPrefs()
    }

    private fun displayPrefs() {
        darkModePref?.summary = resources
            .getStringArray(R.array.night_mode_items)[sharedPrefs.getDarkModeSetting()]

        darkModePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val value = (newValue as String).toIntOrNull() ?: return@OnPreferenceChangeListener false
                AppCompatDelegate.setDefaultNightMode(themeHelper.getNightMode(value))
                sharedPrefs.setDarkModeSetting(value)
                darkModePref?.summary = resources.getStringArray(R.array.night_mode_items)[value]
                true
            }

        themePref?.summary = resources
            .getStringArray(R.array.theme_items)[sharedPrefs.getThemeSetting()]

        themePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val themeSetting = (newValue as String).toIntOrNull() ?: return@OnPreferenceChangeListener false
                sharedPrefs.setThemeSetting(themeSetting)
                requireActivity().recreate()
                true
            }
    }

    private fun regionsPrefs() {
        regionsPref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val index = regionsPref?.findIndexOfValue(newValue.toString()) ?: -1
                regionsPref?.summary = regionsPref?.entries?.getOrNull(index) ?: "-"
                true
            }
    }

    private fun observeWatchProviders() {
        viewModel.data.observe(viewLifecycleOwner) { result ->
            result?.let { showData(it) }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let { regionsPref?.summary = getString(it) }
        }
    }

    private fun showData(list: List<WatchProviderRegion>) {
        regionsPref?.entries = list.map { it.nativeName }.toTypedArray()
        regionsPref?.entryValues = list.map { it.iso }.toTypedArray()

        val summary = list.firstOrNull {
            it.iso == sharedPrefs.getWatchProviderRegion()
        }?.nativeName ?: getString(R.string.select_region)

        regionsPref?.summary = summary
    }
}
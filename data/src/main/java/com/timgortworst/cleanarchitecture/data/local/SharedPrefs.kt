package com.timgortworst.cleanarchitecture.data.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type

class SharedPrefs(
    private val spm: SharedPrefManager,
    @ApplicationContext private val context: Context
) {

    fun setOnboardingDone(isDone: Boolean) {
        spm.setBoolValue(SHARED_PREF_ONBOARDING, isDone)
    }

    fun isOnboardingDone() = spm.getBoolValue(SHARED_PREF_ONBOARDING)

    fun setWatchProviderRegion(region: String) {
        spm.setStringValue(SHARED_PREF_WATCH_PROVIDER_REGION, region)
    }

    fun getWatchProviderRegion() = spm.getStringValue(SHARED_PREF_WATCH_PROVIDER_REGION)

    fun setWatchProviders(watchProviders: List<WatchProvider>) {
        spm.setList(SHARED_PREF_WATCH_PROVIDER, watchProviders)
    }

    fun getWatchProviders(): List<WatchProvider> {
        val json = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(SHARED_PREF_WATCH_PROVIDER, null)
        val type: Type = object : TypeToken<List<WatchProvider>>() {}.type
        return Gson().fromJson(json, type)
    }

    companion object {
        const val SHARED_PREF_ONBOARDING = "SHARED_PREF_ONBOARDING"
        const val SHARED_PREF_WATCH_PROVIDER_REGION = "SHARED_PREF_WATCH_PROVIDER_REGION"
        const val SHARED_PREF_WATCH_PROVIDER = "SHARED_PREF_WATCH_PROVIDER"
    }
}

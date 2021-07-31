package com.timgortworst.cleanarchitecture.data.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type

class SharedPrefs(
    private val spm: SharedPrefManager,
    @ApplicationContext private val context: Context
) {

    fun setWatchProviderRegion(region: String) {
        spm.setStringValue(SHARED_PREF_WATCH_PROVIDER_REGION, region)
    }

    fun getWatchProviderRegion() = spm.getStringValue(SHARED_PREF_WATCH_PROVIDER_REGION)

    fun setWatchProvidersMovie(watchProviders: List<WatchProvider>) {
        spm.setList(SHARED_PREF_WATCH_PROVIDER_MOVIE, watchProviders)
    }

    fun getWatchProvidersMovie(): List<WatchProvider>? {
        val json = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(SHARED_PREF_WATCH_PROVIDER_MOVIE, null) ?: return null
        val type: Type = object : TypeToken<List<WatchProvider>>() {}.type
        return Gson().fromJson(json, type) // FIXME
    }

    fun setWatchProvidersTv(watchProviders: List<WatchProvider>) {
        spm.setList(SHARED_PREF_WATCH_PROVIDER_TV, watchProviders)
    }

    fun getWatchProvidersTv(): List<WatchProvider>? {
        val json = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(SHARED_PREF_WATCH_PROVIDER_TV, null) ?: return null
        val type: Type = object : TypeToken<List<WatchProvider>>() {}.type
        return Gson().fromJson(json, type) // FIXME
    }

    companion object {
        const val SHARED_PREF_WATCH_PROVIDER_REGION = "SHARED_PREF_WATCH_PROVIDER_REGION"
        const val SHARED_PREF_WATCH_PROVIDER_MOVIE = "SHARED_PREF_WATCH_PROVIDER_MOVIE"
        const val SHARED_PREF_WATCH_PROVIDER_TV = "SHARED_PREF_WATCH_PROVIDER_TV"
    }
}

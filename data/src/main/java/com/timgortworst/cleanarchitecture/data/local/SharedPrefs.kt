package com.timgortworst.cleanarchitecture.data.local

import android.content.Context
import com.squareup.moshi.Moshi
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import dagger.hilt.android.qualifiers.ApplicationContext

class SharedPrefs(
    private val spm: SharedPrefManager,
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {
    fun setDarkModeSetting(darkMode: Int) {
        spm.setIntValue(SHARED_PREF_DARK_MODE, darkMode)
    }

    fun getDarkModeSetting() = spm.getIntValue(SHARED_PREF_DARK_MODE)

    fun setThemeSetting(darkMode: Int) {
        spm.setIntValue(SHARED_PREF_THEME, darkMode)
    }

    fun getThemeSetting() = spm.getIntValue(SHARED_PREF_THEME)

    fun getWatchProviderRegion() = spm.getStringValue(SHARED_PREF_WATCH_PROVIDER_REGION)

    fun setWatchProvidersMovie(watchProviders: List<WatchProvider>) {
        spm.setList(context, moshi, SHARED_PREF_WATCH_PROVIDER_MOVIE, watchProviders)
    }

    fun getWatchProvidersMovie() =
        spm.getList<WatchProvider>(context, moshi, SHARED_PREF_WATCH_PROVIDER_MOVIE)

    companion object {
        const val SHARED_PREF_DARK_MODE = "SHARED_PREF_WATCH_PROVIDER_REGION"
        const val SHARED_PREF_THEME = "SHARED_PREF_THEME"
        const val SHARED_PREF_WATCH_PROVIDER_REGION = "regions_key"
        const val SHARED_PREF_WATCH_PROVIDER_MOVIE = "SHARED_PREF_WATCH_PROVIDER_MOVIE"
    }
}

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

    fun setWatchProviderRegion(region: String) {
        spm.setStringValue(SHARED_PREF_WATCH_PROVIDER_REGION, region)
    }

    fun getWatchProviderRegion() = spm.getStringValue(SHARED_PREF_WATCH_PROVIDER_REGION)

    fun setWatchProvidersMovie(watchProviders: List<WatchProvider>) {
        spm.setList(context, moshi, SHARED_PREF_WATCH_PROVIDER_MOVIE, watchProviders)
    }

    fun getWatchProvidersMovie() =
        spm.getList<WatchProvider>(context, moshi, SHARED_PREF_WATCH_PROVIDER_MOVIE)

    fun setWatchProvidersTv(watchProviders: List<WatchProvider>) {
        spm.setList(context, moshi, SHARED_PREF_WATCH_PROVIDER_TV, watchProviders)
    }

    fun getWatchProvidersTv() =
        spm.getList<WatchProvider>(context, moshi, SHARED_PREF_WATCH_PROVIDER_TV)

    companion object {
        const val SHARED_PREF_WATCH_PROVIDER_REGION = "SHARED_PREF_WATCH_PROVIDER_REGION"
        const val SHARED_PREF_WATCH_PROVIDER_MOVIE = "SHARED_PREF_WATCH_PROVIDER_MOVIE"
        const val SHARED_PREF_WATCH_PROVIDER_TV = "SHARED_PREF_WATCH_PROVIDER_TV"
    }
}

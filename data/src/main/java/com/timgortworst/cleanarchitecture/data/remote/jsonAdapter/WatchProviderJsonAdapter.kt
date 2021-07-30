package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.watchprovider.NetworkWatchProviders
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider

class WatchProviderJsonAdapter {

    @FromJson
    fun fromJson(networkWatchProviders: NetworkWatchProviders): List<WatchProvider>? {
        val results = networkWatchProviders.results
        if (results.isNullOrEmpty()) return null

        return results.map {
            WatchProvider(
                it.displayPriority ?: 0,
                it.logoPath.orEmpty(),
                it.providerName ?: return null,
                it.providerId ?: return null
            )
        }
    }
}
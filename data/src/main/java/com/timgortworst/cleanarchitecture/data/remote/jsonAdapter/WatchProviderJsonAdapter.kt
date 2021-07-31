package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProvidersJson
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider

class WatchProviderJsonAdapter {

    @FromJson
    fun fromJson(watchProvidersJson: WatchProvidersJson): List<WatchProvider>? {
        val results = watchProvidersJson.results
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
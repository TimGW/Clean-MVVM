package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.watchprovider.NetworkWatchProviderRegions
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion

class WatchProviderRegionJsonAdapter {

    @FromJson
    fun fromJson(networkWatchProviderRegions: NetworkWatchProviderRegions): List<WatchProviderRegion>? {
        val results = networkWatchProviderRegions.results
        if (results.isNullOrEmpty()) return null

        return results.map {
            WatchProviderRegion(
                it.iso ?: return null,
                it.englishName ?: return null,
                it.nativeName ?: return null
            )
        }
    }
}
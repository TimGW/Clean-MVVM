package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsJson
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion

class WatchProviderRegionJsonAdapter {

    @FromJson
    fun fromJson(watchProviderRegionsJson: WatchProviderRegionsJson): List<WatchProviderRegion>? {
        val results = watchProviderRegionsJson.results
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
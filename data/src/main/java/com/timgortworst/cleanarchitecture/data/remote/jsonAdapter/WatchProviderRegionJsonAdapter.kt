package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsEntity
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsJson

class WatchProviderRegionJsonAdapter {

    @FromJson
    fun fromJson(watchProviderRegionsJson: WatchProviderRegionsJson): List<WatchProviderRegionsEntity>? {
        val results = watchProviderRegionsJson.results
        if (results.isNullOrEmpty()) return null

        return results.map {
            WatchProviderRegionsEntity(
                it.iso ?: return null,
                it.englishName ?: return null,
                it.nativeName ?: return null
            )
        }
    }
}
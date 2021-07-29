package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.watchprovider.NetworkWatchProviderRegions
import com.timgortworst.cleanarchitecture.data.model.watchprovider.NetworkWatchProviders
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion

fun NetworkWatchProviderRegions.asDomainModel() = with(this) {
    results.map {
        WatchProviderRegion(
            it.iso,
            it.englishName,
            it.nativeName
        )
    }
}

fun NetworkWatchProviders.asDomainModel() = with(this) {
    results.map {
        WatchProvider(
            it.displayPriority,
            it.logoPath,
            it.providerName,
            it.providerId,
        )
    }
}
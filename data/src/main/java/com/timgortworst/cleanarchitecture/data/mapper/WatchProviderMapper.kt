package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProviderRegions
import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProvidersMovie
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProviderRegion

fun NetworkWatchProviderRegions.asDomainModel() = with(this) {
    results.map {
        WatchProviderRegion(
            it.iso,
            it.englishName,
            it.nativeName
        )
    }
}

fun NetworkWatchProvidersMovie.asDomainModel() = with(this) {
    results.map {
        WatchProvider(
            it.displayPriority,
            it.logoPath,
            it.providerName,
            it.providerId,
        )
    }
}
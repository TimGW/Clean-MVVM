package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProviderRegions
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
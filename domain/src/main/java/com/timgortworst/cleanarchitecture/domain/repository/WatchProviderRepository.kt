package com.timgortworst.cleanarchitecture.domain.repository

import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.Resource

interface WatchProviderRepository {

    suspend fun getWatchProviderRegions(): Resource<List<WatchProviderRegion>>
}

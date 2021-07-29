package com.timgortworst.cleanarchitecture.domain.repository

import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.Resource

interface WatchProviderRepository {

    suspend fun getWatchProviderRegions(): Resource<List<WatchProviderRegion>>

    suspend fun getWatchProvidersMovie(region: String): Resource<List<WatchProvider>>

    suspend fun getWatchProvidersTv(region: String): Resource<List<WatchProvider>>
}

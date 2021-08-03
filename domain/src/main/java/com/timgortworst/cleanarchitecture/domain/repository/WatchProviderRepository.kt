package com.timgortworst.cleanarchitecture.domain.repository

import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.Result

interface WatchProviderRepository {

    suspend fun getWatchProviderRegions(): Result<List<WatchProviderRegion>>

    suspend fun getWatchProvidersMovie(region: String): Result<List<WatchProvider>>

    suspend fun getWatchProvidersTv(region: String): Result<List<WatchProvider>>
}

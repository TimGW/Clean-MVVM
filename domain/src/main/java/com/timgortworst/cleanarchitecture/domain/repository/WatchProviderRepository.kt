package com.timgortworst.cleanarchitecture.domain.repository

import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import kotlinx.coroutines.flow.Flow

interface WatchProviderRepository {

    fun getWatchProviderRegions(): Flow<Result<List<WatchProviderRegion>?>>

    suspend fun getWatchProvidersMovie(region: String): Result<List<WatchProvider>>

    suspend fun getWatchProvidersTv(region: String): Result<List<WatchProvider>>
}

package com.timgortworst.cleanarchitecture.data.remote

import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsEntity
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchProviderService {

    @GET("watch/providers/regions")
    suspend fun getWatchProviderRegions(): Response<List<WatchProviderRegionsEntity>>

    @GET("watch/providers/movie")
    suspend fun getWatchProvidersMovie(
        @Query("watch_region") region: String
    ): Response<List<WatchProvider>>

    @GET("watch/providers/tv")
    suspend fun getWatchProvidersTv(
        @Query("watch_region") region: String
    ): Response<List<WatchProvider>>
}

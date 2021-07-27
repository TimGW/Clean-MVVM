package com.timgortworst.cleanarchitecture.data.remote

import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProviderRegions
import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProviders
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchProviderService {

    @GET("watch/providers/regions")
    suspend fun getWatchProviderRegions(): Response<NetworkWatchProviderRegions>

    @GET("watch/providers/movie")
    suspend fun getWatchProvidersMovie(
        @Query("watch_region") region: String
    ): Response<NetworkWatchProviders>

    @GET("watch/providers/tv")
    suspend fun getWatchProvidersTv(
        @Query("watch_region") region: String
    ): Response<NetworkWatchProviders>
}

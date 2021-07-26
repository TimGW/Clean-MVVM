package com.timgortworst.cleanarchitecture.data.network

import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProviderRegions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchProviderService {

    @GET("watch/providers/regions")
    suspend fun getWatchProviderRegions(): Response<NetworkWatchProviderRegions>
//
//    @GET("watch/providers/movie")
//    suspend fun getWatchProvidersMovie(
//        @Query("watch_region") region: String
//    ): Response<NetworkWatchProviderRegions>
//
//    @GET("watch/providers/tv")
//    suspend fun getWatchProvidersTv(
//        @Query("watch_region") region: String
//    ): Response<NetworkWatchProviderRegions>
}

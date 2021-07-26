package com.timgortworst.cleanarchitecture.data.network

import com.timgortworst.cleanarchitecture.data.model.NetworkWatchProviderRegions
import retrofit2.Response
import retrofit2.http.GET

interface WatchProviderService {

    @GET("watch/providers/regions")
    suspend fun getWatchProviderRegions(): Response<NetworkWatchProviderRegions>
}

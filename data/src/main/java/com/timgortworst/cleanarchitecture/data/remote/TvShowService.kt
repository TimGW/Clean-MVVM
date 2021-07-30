package com.timgortworst.cleanarchitecture.data.remote

import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShows
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("discover/tv")
    suspend fun getTvShows(
        @Query("page") page: Int,
        @Query("region") region: String? = null,
        @Query("with_watch_providers") watchProviderIds: String? = null,
        @Query("watch_region") watchProviderRegion: String? = null,
        @Query("with_watch_monetization_types") monetizationTypes: String? = null,
    ): Response<TvShows>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("append_to_response") providers: String = "watch/providers",
    ): Response<TvShowDetails>
}

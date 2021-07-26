package com.timgortworst.cleanarchitecture.data.network

import com.timgortworst.cleanarchitecture.data.model.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.data.model.NetworkMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,

        // default is netflix OR amazon
        @Query("with_watch_providers") providerIds: String = "8|9",

        // Default region is NL
        @Query("watch_region") region: String = "NL",

        // flatrate, free, ads, rent, buy
        @Query("with_watch_monetization_types") type: String = "flatrate|free|ads",
    ): Response<NetworkMovies>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") providers: String = "watch/providers",
    ): Response<NetworkMovieDetails>
}

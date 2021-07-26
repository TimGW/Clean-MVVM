package com.timgortworst.cleanarchitecture.data.network

import com.timgortworst.cleanarchitecture.data.model.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.data.model.NetworkMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int): Response<NetworkMovies>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<NetworkMovieDetails>
}

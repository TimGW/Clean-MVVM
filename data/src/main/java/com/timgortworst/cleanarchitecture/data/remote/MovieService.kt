package com.timgortworst.cleanarchitecture.data.remote

import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("region") region: String? = null,
        @Query("with_watch_providers") watchProviderIds: String? = null,
        @Query("watch_region") watchProviderRegion: String? = null,
        @Query("with_watch_monetization_types") monetizationTypes: String? = null,
    ): Response<Movies>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") providers: String = "watch/providers",
    ): Response<MovieDetailsEntity>

    // todo append to moviedetails call
    @GET("movie/{movie_id}/recommendations")
    suspend fun getRelatedMovies(
        @Path("movie_id") movieId: Int,
    ): Response<Movies>

    // todo append to moviedetails call
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
    ): Response<Credits>
}

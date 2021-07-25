package com.timgortworst.cleanarchitecture.domain.repository

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import kotlinx.coroutines.flow.Flow
import com.timgortworst.cleanarchitecture.domain.model.state.Resource

interface MovieRepository {

    /**
     * one shot operation to fetch the movies from network
     */
    suspend fun getMovies(): Resource<List<Movie>>

    /**
     * Fetch the movie details from network and store it in database.
     * At the end, data from persistence storage is fetched and emitted.
     */
    fun getMovieDetailFlow(movieId: Int): Flow<Resource<List<MovieDetails>>>
}

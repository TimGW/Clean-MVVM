package com.timgortworst.cleanarchitecture.domain.repository

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import kotlinx.coroutines.flow.Flow
import com.timgortworst.cleanarchitecture.domain.model.state.Resource

interface MovieRepository {
    /**
     * Fetch the movies from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getMoviesFlow(): Flow<Resource<List<Movie>>>

    /**
     * one shot operation to fetch the movie details from network
     */
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails>
}

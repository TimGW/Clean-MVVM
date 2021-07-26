package com.timgortworst.cleanarchitecture.domain.repository

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(): Resource<List<Movie>>

    fun getPagedMovies(): Flow<PagingData<Movie>>

    fun getMovieDetailFlow(movieId: Int): Flow<Resource<List<MovieDetails>>>
}

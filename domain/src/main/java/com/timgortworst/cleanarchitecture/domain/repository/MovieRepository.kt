package com.timgortworst.cleanarchitecture.domain.repository

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPagedMovies(): Flow<PagingData<Movie>>
    fun getRelatedMovies(movieId: Int): Flow<Result<List<Movie>>>
    fun getMovieCredits(movieId: Int): Flow<Result<Credits>>
    fun getMovieDetailFlow(movieId: Int): Flow<Result<out MovieDetails?>>
}

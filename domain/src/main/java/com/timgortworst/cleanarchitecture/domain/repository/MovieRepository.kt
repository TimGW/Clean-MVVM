package com.timgortworst.cleanarchitecture.domain.repository

import androidx.paging.PagingSource
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPagedMovieSource(): PagingSource<Int, Movie>

    fun getMovieDetailFlow(movieId: Int): Flow<Resource<List<MovieDetails>>>
}

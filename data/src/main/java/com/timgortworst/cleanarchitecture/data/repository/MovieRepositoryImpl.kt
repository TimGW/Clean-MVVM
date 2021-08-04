package com.timgortworst.cleanarchitecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.timgortworst.cleanarchitecture.data.local.MovieDao
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.data.remote.MovieService
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val errorHandler: ErrorHandler,
    private val sharedPrefs: SharedPrefs
) : MovieRepository {

    override fun getPagedMovies() = Pager(
        PagingConfig(pageSize = 100)
    ) {
        MoviePagingSource(movieService, sharedPrefs)
    }.flow

    override fun getMovieDetailFlow(
        movieId: Int
    ) = object : NetworkBoundResource<MovieDetailsEntity, MovieDetails?>() {

        override suspend fun saveRemoteData(response: MovieDetailsEntity) {
            movieDao.insertMovieDetails(response)
        }

        override fun fetchFromLocal() = movieDao.getMovieDetailsDistinctUntilChanged(movieId).map {
            it?.toMovieDetails()
        }

        override suspend fun fetchFromRemote() = movieService.getMovieDetails(movieId)

        override suspend fun errorHandler() = errorHandler

        override fun shouldFetch(data: MovieDetails?) = data == null // TODO provide stale cache timeout

    }.asFlow().flowOn(Dispatchers.IO)
}
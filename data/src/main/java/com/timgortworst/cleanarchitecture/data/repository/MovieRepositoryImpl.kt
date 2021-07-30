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
    ) = object : NetworkBoundResource<MovieDetails, List<MovieDetails>>() {

        override suspend fun saveRemoteData(response: MovieDetails) =
            movieDao.insertMovieDetails(MovieDetailsEntity.from(response))//(sharedPrefs.getWatchProviderRegion()))

        override fun fetchFromLocal() = movieDao.getMovieDetails(movieId).map { list ->
            list.map { movie -> movie.toMovieDetails() }
        }

        override suspend fun fetchFromRemote() = movieService.getMovieDetails(movieId)

        override suspend fun getErrorHandler() = errorHandler

        override fun shouldFetch(data: List<MovieDetails>?) = data.isNullOrEmpty()

    }.asFlow().flowOn(Dispatchers.IO)
}
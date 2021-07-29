package com.timgortworst.cleanarchitecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.timgortworst.cleanarchitecture.data.local.MovieDao
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.mapper.asDatabaseModel
import com.timgortworst.cleanarchitecture.data.mapper.asDomainModel
import com.timgortworst.cleanarchitecture.data.model.movie.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.data.remote.MovieService
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Lazy cache repository for fetching videos from the network and storing them on disk
 *
 * @property movieService
 * @property movieDao
 */
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
    ) = object : NetworkBoundResource<NetworkMovieDetails, List<MovieDetails>>() {

        override suspend fun saveRemoteData(response: NetworkMovieDetails) =
            movieDao.insertMovieDetails(response.asDatabaseModel(sharedPrefs.getWatchProviderRegion()))

        override fun fetchFromLocal() = movieDao.getMovieDetails(movieId).map { list ->
            list.map { movie -> movie.asDomainModel() }
        }

        override suspend fun fetchFromRemote() = movieService.getMovieDetails(movieId)

        override suspend fun getErrorHandler() = errorHandler

        override fun shouldFetch(data: List<MovieDetails>?) = data.isNullOrEmpty()

    }.asFlow().flowOn(Dispatchers.IO)
}
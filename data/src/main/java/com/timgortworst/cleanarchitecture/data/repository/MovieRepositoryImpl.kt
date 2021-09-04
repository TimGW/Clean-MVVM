package com.timgortworst.cleanarchitecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.timgortworst.cleanarchitecture.data.local.MovieDao
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.data.remote.MovieService
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
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

    override fun getRelatedMovies(movieId: Int) = flow {
        emit(Result.Loading(null))
        try {
            val apiResponse = movieService.getRelatedMovies(movieId)
            val remoteResponse = apiResponse.body()

            if (apiResponse.isSuccessful) {
                emit(Result.Success(remoteResponse?.results.orEmpty()))
            } else {
                emit(Result.Error(errorHandler.getApiError(apiResponse.code()), null))
            }
        } catch (e: Exception) {
            emit(Result.Error(errorHandler.getError(e), null))
        }
    }

    override fun getMovieDetailFlow(
        movieId: Int
    ) = object : NetworkBoundResource<MovieDetailsEntity, MovieDetails?>(errorHandler) {

        override suspend fun saveRemoteData(response: MovieDetailsEntity) {
            movieDao.insertMovieDetails(response)
        }

        override fun fetchFromLocal() = movieDao.getMovieDetailsDistinctUntilChanged(movieId).map {
            it?.toMovieDetails()
        }

        override suspend fun fetchFromRemote() = movieService.getMovieDetails(movieId)

        override fun shouldFetch(data: MovieDetails?) =
            (data == null || isMovieStale(data.modifiedAt))

    }.asFlow().flowOn(Dispatchers.IO)

    private fun isMovieStale(lastUpdated: Long): Boolean {
        val oneDay = TimeUnit.DAYS.toMillis(1)
        return (System.currentTimeMillis() - oneDay) > lastUpdated
    }
}
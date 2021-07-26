package com.timgortworst.cleanarchitecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.data.database.MovieDao
import com.timgortworst.cleanarchitecture.data.mapper.asDatabaseModel
import com.timgortworst.cleanarchitecture.data.mapper.asDomainModel
import com.timgortworst.cleanarchitecture.data.model.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.data.network.MovieService
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val errorHandler: ErrorHandler
) : MovieRepository {

    override fun getPagedMovies(): Flow<PagingData<Movie>> = Pager(
        PagingConfig(pageSize = 100)
    ) {
        MoviePagingSource(movieService)
    }.flow

    override suspend fun getMovies(): Resource<List<Movie>> {
        return try {
            val apiResponse = movieService.getMovies(1)
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                Resource.Success(data.asDomainModel())
            } else {
                Resource.Error(errorHandler.getApiError(
                    statusCode = apiResponse.code(),
                    message = apiResponse.message()
                ))
            }
        } catch (e: Throwable) {
            Resource.Error(errorHandler.getError(e))
        }
    }

    override fun getMovieDetailFlow(
        movieId: Int
    ) = object : NetworkBoundResource<NetworkMovieDetails, List<MovieDetails>>() {

        override suspend fun saveRemoteData(response: NetworkMovieDetails) =
            movieDao.insertMovieDetails(response.asDatabaseModel())

        override fun fetchFromLocal() = movieDao.getMovieDetails(movieId).map { list ->
            list.map { movie -> movie.asDomainModel() }
        }

        override suspend fun fetchFromRemote() = movieService.getMovieDetails(movieId)

        override suspend fun getErrorHandler() = errorHandler

        override fun shouldFetch(data: List<MovieDetails>?) = data.isNullOrEmpty()

    }.asFlow().flowOn(Dispatchers.IO)
}
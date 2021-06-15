package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.data.database.LocalDataSourceMovie
import com.timgortworst.cleanarchitecture.data.mapper.asDatabaseModel
import com.timgortworst.cleanarchitecture.data.mapper.asDomainModel
import com.timgortworst.cleanarchitecture.data.model.NetworkMovies
import com.timgortworst.cleanarchitecture.data.network.RemoteDataSourceMovie
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Lazy cache repository for fetching videos from the network and storing them on disk
 *
 * @property remoteDataSourceMovie
 * @property localDataSourceMovie
 */
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSourceMovie: RemoteDataSourceMovie,
    private val localDataSourceMovie: LocalDataSourceMovie,
    private val errorHandler: ErrorHandler
) : MovieRepository {

    override fun getMoviesFlow() = object : NetworkBoundRepository<NetworkMovies, List<Movie>>() {

        override suspend fun saveRemoteData(response: NetworkMovies) =
            localDataSourceMovie.insertMovies(response.asDatabaseModel())

        override fun fetchFromLocal() = localDataSourceMovie.getMovies().map { dbMovieList ->
            dbMovieList.map { it.asDomainModel() }
        }

        override suspend fun fetchFromRemote() = remoteDataSourceMovie.getMovies()

        override suspend fun getErrorHandler() = errorHandler

    }.asFlow().flowOn(Dispatchers.IO)

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        return try {
            val apiResponse = remoteDataSourceMovie.getMovieDetails(movieId)
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
}
package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.data.database.LocalDataSourceMovie
import com.timgortworst.cleanarchitecture.data.mapper.asDatabaseModel
import com.timgortworst.cleanarchitecture.data.mapper.asDomainModel
import com.timgortworst.cleanarchitecture.data.model.NetworkMovies
import com.timgortworst.cleanarchitecture.data.network.RemoteDataSourceMovie
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.response.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.response.State
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response

/**
 * Lazy cache repository for fetching videos from the network and storing them on disk
 *
 * @property remoteDataSourceMovie
 * @property localDataSourceMovie
 */
class MovieRepositoryImpl(
    private val remoteDataSourceMovie: RemoteDataSourceMovie,
    private val localDataSourceMovie: LocalDataSourceMovie,
    private val errorHandler: ErrorHandler
) : MovieRepository {

    override fun getMoviesFlow(): Flow<State<List<Movie>>> {
        return object : NetworkBoundRepository<NetworkMovies, List<Movie>>() {

            override suspend fun saveRemoteData(response: NetworkMovies) =
                localDataSourceMovie.insertMovies(response.asDatabaseModel())

            override fun fetchFromLocal(): Flow<List<Movie>> =
                localDataSourceMovie.getMovies().map { dbMovieList ->
                    dbMovieList.map { it.asDomainModel() }
                }

            override suspend fun fetchFromRemote(): Response<NetworkMovies> =
                remoteDataSourceMovie.getMovies()

            override suspend fun getErrorHandler() = errorHandler

        }.asFlow().flowOn(Dispatchers.IO)
    }

    override suspend fun getMovieDetails(movieId: Int): State<MovieDetails> {
        return try {
            val apiResponse = remoteDataSourceMovie.getMovieDetails(movieId)
            val data = apiResponse.body()

           if (apiResponse.isSuccessful && data != null) {
               State.Success(data.asDomainModel())
           } else {
               State.Error(errorHandler.getError(apiResponse.code()))
           }
        } catch (e: Throwable) {
            State.Error(errorHandler.getError(e))
        }
    }
}
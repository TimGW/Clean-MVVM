package com.timgortworst.cleanarchitecture.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 */
abstract class NetworkBoundResource<RequestType, ResultType> {

    fun asFlow() = flow {
        val localResult = fetchFromLocal().first() // todo firstOrNull ??

        try {
            emit(Resource.Loading(localResult))

            if (shouldFetch(localResult)) {
                fetchFromNetwork(localResult).collect()
            } else {
                emit(Resource.Success(localResult))
            }
        } catch (e: Exception) {
            emit(Resource.Error(getErrorHandler().getError(e), localResult))
        } finally {
            // emit all from DB TODO map really required???
            emitAll(fetchFromLocal().map { Resource.Success(it) })
        }
    }

    private fun fetchFromNetwork(
        localResult: ResultType?
    ) = flow {
        val apiResponse = fetchFromRemote()
        val remoteResponse = apiResponse.body()

        if (apiResponse.isSuccessful && remoteResponse != null) {
            saveRemoteData(remoteResponse)
        } else {
            val error = getErrorHandler().getApiError(
                statusCode = apiResponse.code(),
                message = apiResponse.message()
            )
            emit(Resource.Error(error, localResult))
        }
    }

    protected abstract suspend fun getErrorHandler() : ErrorHandler

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: RequestType)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RequestType>

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean
}

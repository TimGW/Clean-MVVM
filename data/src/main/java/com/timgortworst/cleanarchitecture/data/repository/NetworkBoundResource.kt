package com.timgortworst.cleanarchitecture.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * Guide to app architecture:
 * https://developer.android.com/jetpack/guide
 *
 * @param <ResultType> Represents the domain model
 * @param <RequestType> Represents the (converted) network > database model
 */
abstract class NetworkBoundResource<RequestType, ResultType> {

    fun asFlow() = flow {
        val localResult = fetchFromLocal().firstOrNull()

        // first emit loading state with cached result
        emit(Result.Loading(localResult))

        // check for cache invalidation rules
        if (shouldFetch(localResult)) {

            // retrieve fresh data from network
            fetchFromNetwork(localResult).collect()
        } else {

            // cache is not stale or null, return cached result
            emit(Result.Success(localResult))
        }

        // finally re-emit the values from the Database acting as the single source of truth
        emitAll(fetchFromLocal().map { Result.Success(it) })
    }

    private fun fetchFromNetwork(
        localResult: ResultType?
    ) = flow {
        val apiResponse = fetchFromRemote()
        val remoteResponse = apiResponse.body()

        if (apiResponse.isSuccessful && remoteResponse != null) {

            // store the fetched data in the database
            saveRemoteData(remoteResponse)
        } else {

            // an exception occurred, emit error state with cached result
            emit(Result.Error(errorHandler().getApiError(apiResponse.code()), localResult))
        }
    }

    protected abstract suspend fun errorHandler() : ErrorHandler

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: RequestType)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RequestType>

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean
}

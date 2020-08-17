package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.domain.model.response.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.response.State
import kotlinx.coroutines.flow.*
import retrofit2.Response


/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [NETWORK] represents the type for network.
 * [DOMAIN] represents the type for domain.
 */
abstract class NetworkBoundRepository<NETWORK, DOMAIN> {

    fun asFlow() = flow {
        emit(State.Loading)
        emit(State.Success(fetchFromLocal().first())) // Emit Database content first

        val apiResponse = fetchFromRemote()
        val remotePosts = apiResponse.body()

        if (apiResponse.isSuccessful && remotePosts != null) {
            saveRemoteData(remotePosts)
        } else {
            emit(State.Error(getErrorHandler().getError(apiResponse.code())))
        }

        emitAll(fetchFromLocal().map { State.Success(it) })
    }.catch { e ->
        emit(State.Error(getErrorHandler().getError(e)))
        e.printStackTrace()
    }

    /**
     * set an error handler
     */
    protected abstract suspend fun getErrorHandler() : ErrorHandler

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    protected abstract suspend fun saveRemoteData(response: NETWORK)

    /**
     * Retrieves all data from persistence storage.
     */
    protected abstract fun fetchFromLocal(): Flow<DOMAIN>

    /**
     * Fetches [State] from the remote end point.
     */
    protected abstract suspend fun fetchFromRemote(): Response<NETWORK>
}

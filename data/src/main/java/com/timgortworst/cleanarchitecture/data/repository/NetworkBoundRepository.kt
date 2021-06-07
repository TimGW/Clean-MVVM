package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
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
        emit(Resource.Loading)
        emit(Resource.Success(fetchFromLocal().first())) // Emit Database content first

        val apiResponse = fetchFromRemote()
        val remotePosts = apiResponse.body()

        if (apiResponse.isSuccessful && remotePosts != null) {
            saveRemoteData(remotePosts)
        } else {
            emit(Resource.Error(getErrorHandler().getError(apiResponse.code())))
        }

        emitAll(fetchFromLocal().map { Resource.Success(it) })
    }.catch { e ->
        emit(Resource.Error(getErrorHandler().getError(e)))
        e.printStackTrace()
    }

    protected abstract suspend fun getErrorHandler() : ErrorHandler

    protected abstract suspend fun saveRemoteData(response: NETWORK)

    protected abstract fun fetchFromLocal(): Flow<DOMAIN>

    protected abstract suspend fun fetchFromRemote(): Response<NETWORK>
}

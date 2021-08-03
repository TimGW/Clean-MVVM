package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.data.remote.WatchProviderService
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import retrofit2.Response
import javax.inject.Inject

// TODO caching with networkboundresource
class WatchProviderRepositoryImpl @Inject constructor(
    private val service: WatchProviderService,
    private val errorHandler: ErrorHandler
) : WatchProviderRepository {

    override suspend fun getWatchProviderRegions() = try {
        apiResult(service.getWatchProviderRegions())
    } catch (e: Throwable) {
        Result.Error(errorHandler.getError(e))
    }

    override suspend fun getWatchProvidersMovie(region: String) = try {
        apiResult(service.getWatchProvidersMovie(region))
    } catch (e: Throwable) {
        Result.Error(errorHandler.getError(e))
    }

    override suspend fun getWatchProvidersTv(region: String) = try {
        apiResult(service.getWatchProvidersTv(region))
    } catch (e: Throwable) {
        Result.Error(errorHandler.getError(e))
    }

    private fun <T> apiResult(response: Response<T>): Result<T> {
        val data = response.body()
        return if (response.isSuccessful && data != null) {
            Result.Success(data)
        } else {
            Result.Error(errorHandler.getApiError(response.code()))
        }
    }
}
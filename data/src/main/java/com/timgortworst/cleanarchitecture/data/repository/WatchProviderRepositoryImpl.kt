package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.data.remote.WatchProviderService
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import javax.inject.Inject

// TODO caching
class WatchProviderRepositoryImpl @Inject constructor(
    private val service: WatchProviderService,
    private val errorHandler: ErrorHandler
) : WatchProviderRepository {

    override suspend fun getWatchProviderRegions(): Resource<List<WatchProviderRegion>> {
        return try {
            val apiResponse = service.getWatchProviderRegions()
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                Resource.Success(data)
            } else {
                Resource.Error(
                    errorHandler.getApiError(
                        statusCode = apiResponse.code(),
                        message = apiResponse.message()
                    )
                )
            }
        } catch (e: Throwable) {
            Resource.Error(errorHandler.getError(e))
        }
    }

    override suspend fun getWatchProvidersMovie(region: String): Resource<List<WatchProvider>> {
        return try {
            val apiResponse = service.getWatchProvidersMovie(region)
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                Resource.Success(data)
            } else {
                Resource.Error(
                    errorHandler.getApiError(
                        statusCode = apiResponse.code(),
                        message = apiResponse.message()
                    )
                )
            }
        } catch (e: Throwable) {
            Resource.Error(errorHandler.getError(e))
        }
    }

    override suspend fun getWatchProvidersTv(region: String): Resource<List<WatchProvider>> {
        return try {
            val apiResponse = service.getWatchProvidersTv(region)
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                Resource.Success(data)
            } else {
                Resource.Error(
                    errorHandler.getApiError(
                        statusCode = apiResponse.code(),
                        message = apiResponse.message()
                    )
                )
            }
        } catch (e: Throwable) {
            Resource.Error(errorHandler.getError(e))
        }
    }
}
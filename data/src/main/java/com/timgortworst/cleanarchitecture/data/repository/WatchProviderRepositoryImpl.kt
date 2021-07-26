package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.data.mapper.asDomainModel
import com.timgortworst.cleanarchitecture.data.network.WatchProviderService
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import javax.inject.Inject

class WatchProviderRepositoryImpl @Inject constructor(
    private val service: WatchProviderService,
    private val errorHandler: ErrorHandler
) : WatchProviderRepository {

    override suspend fun getWatchProviderRegions(): Resource<List<WatchProviderRegion>> {
        return try {
            val apiResponse = service.getWatchProviderRegions()
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                Resource.Success(data.asDomainModel())
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
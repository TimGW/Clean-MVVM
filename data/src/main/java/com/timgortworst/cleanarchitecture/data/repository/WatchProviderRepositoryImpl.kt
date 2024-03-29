package com.timgortworst.cleanarchitecture.data.repository

import com.timgortworst.cleanarchitecture.data.di.IoDispatcher
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.local.WatchProviderDao
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsEntity
import com.timgortworst.cleanarchitecture.data.remote.WatchProviderService
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class WatchProviderRepositoryImpl @Inject constructor(
    private val service: WatchProviderService,
    private val watchProviderDao: WatchProviderDao,
    private val errorHandler: ErrorHandler,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val sharedPrefs: SharedPrefs
) : WatchProviderRepository {

    override fun getUserWatchProviderRegion() = sharedPrefs.getWatchProviderRegion()

    override fun getWatchProviderRegions(
    ) = object : NetworkBoundResource<List<WatchProviderRegionsEntity>, List<WatchProviderRegion>?>(
        errorHandler
    ) {
        override suspend fun saveRemoteData(response: List<WatchProviderRegionsEntity>) {
            watchProviderDao.insertWatchProviderRegions(response)
        }

        override fun fetchFromLocal() = watchProviderDao
            .getWatchProviderRegionsDistinctUntilChanged().map {
                it?.map { item -> item.toWatchProviderRegion() }
            }

        override suspend fun fetchFromRemote() = service.getWatchProviderRegions()

        override fun shouldFetch(data: List<WatchProviderRegion>?) = data.isNullOrEmpty()

    }.asFlow().flowOn(dispatcher)

    override suspend fun getWatchProvidersMovie(region: String) = try {
        apiResult(service.getWatchProvidersMovie(region))
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
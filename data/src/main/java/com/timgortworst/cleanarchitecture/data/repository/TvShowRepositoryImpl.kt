package com.timgortworst.cleanarchitecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.local.TvShowDao
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowDetailsEntity
import com.timgortworst.cleanarchitecture.data.remote.TvShowService
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.repository.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowService: TvShowService,
    private val tvShowDao: TvShowDao,
    private val errorHandler: ErrorHandler,
    private val sharedPrefs: SharedPrefs
) : TvShowRepository {

    override fun getTvShows() = Pager(
        PagingConfig(pageSize = 100)
    ) {
        TvShowPagingSource(tvShowService, sharedPrefs)
    }.flow

    override fun getTvShowDetails(
        tvShowId: Int
    ) = object : NetworkBoundResource<TvShowDetails, List<TvShowDetails>>() {

        override suspend fun saveRemoteData(response: TvShowDetails) =
            tvShowDao.insertTvShowDetails(TvShowDetailsEntity.from(response)) //.asDatabaseModel(sharedPrefs.getWatchProviderRegion()))

        override fun fetchFromLocal() = tvShowDao.getTvShowDetails(tvShowId).map { list ->
            list.map { tvShow -> tvShow.toTvShowDetails() }
        }

        override suspend fun fetchFromRemote() = tvShowService.getTvShowDetails(tvShowId)

        override suspend fun getErrorHandler() = errorHandler

        override fun shouldFetch(data: List<TvShowDetails>?) = data.isNullOrEmpty()

    }.asFlow().flowOn(Dispatchers.IO)
}
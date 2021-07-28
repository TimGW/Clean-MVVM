package com.timgortworst.cleanarchitecture.domain.repository

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun getTvShows(): Flow<PagingData<TvShow>>

    fun getTvShowDetails(tvShowId: Int): Flow<Resource<List<TvShowDetails>>>
}

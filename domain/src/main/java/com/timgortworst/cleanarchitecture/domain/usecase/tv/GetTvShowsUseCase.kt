package com.timgortworst.cleanarchitecture.domain.usecase.tv

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase

interface GetTvShowsUseCase : FlowUseCase<Unit, PagingData<TvShow>>
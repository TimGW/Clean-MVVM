package com.timgortworst.cleanarchitecture.domain.usecase.tv

import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase

interface GetTvShowDetailsUseCase :
    FlowUseCase<GetTvShowDetailsUseCaseImpl.Params, Resource<TvShowDetails>>

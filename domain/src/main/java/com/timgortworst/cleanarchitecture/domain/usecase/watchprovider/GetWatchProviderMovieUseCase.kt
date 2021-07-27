package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetWatchProviderMovieUseCase :
    SuspendUseCase<GetWatchProviderMovieUseCaseImpl.Params, Resource<List<WatchProvider>>>
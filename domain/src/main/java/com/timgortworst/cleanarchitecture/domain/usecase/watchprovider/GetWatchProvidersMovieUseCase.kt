package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetWatchProvidersMovieUseCase :
    SuspendUseCase<GetWatchProvidersMovieUseCaseImpl.Params, Result<List<WatchProvider>>>
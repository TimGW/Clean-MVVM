package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetWatchProviderRegionsUseCase : SuspendUseCase<Unit, Result<List<WatchProviderRegion>>>
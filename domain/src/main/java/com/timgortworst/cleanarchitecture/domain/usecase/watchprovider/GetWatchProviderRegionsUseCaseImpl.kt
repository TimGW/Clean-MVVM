package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import javax.inject.Inject

class GetWatchProviderRegionsUseCaseImpl @Inject constructor(
    private val repository: WatchProviderRepository,
) : GetWatchProviderRegionsUseCase {

    override fun execute(params: Unit) = repository.getWatchProviderRegions()
}
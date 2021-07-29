package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import javax.inject.Inject

class GetWatchProvidersTvUseCaseImpl @Inject constructor(
    private val repository: WatchProviderRepository,
) : GetWatchProvidersTvUseCase {

    data class Params(val region: String)

    override suspend fun execute(params: Params) = repository.getWatchProvidersMovie(params.region)
}
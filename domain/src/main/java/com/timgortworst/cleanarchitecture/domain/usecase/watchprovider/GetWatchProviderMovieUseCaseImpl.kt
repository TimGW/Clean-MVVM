package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import javax.inject.Inject

class GetWatchProviderMovieUseCaseImpl @Inject constructor(
    private val repository: WatchProviderRepository,
) : GetWatchProviderMovieUseCase {

    data class Params(val region: String)

    override suspend fun execute(params: Params) = repository.getWatchProviders(params.region)
}
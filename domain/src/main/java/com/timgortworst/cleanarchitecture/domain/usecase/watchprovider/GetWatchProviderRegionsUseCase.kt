package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchProviderRegionsUseCase @Inject constructor(
    private val repository: WatchProviderRepository,
) : UseCase<Unit, Flow<@JvmSuppressWildcards Result<List<WatchProviderRegion>?>>> {

    override fun execute(params: Unit) = repository.getWatchProviderRegions()
}
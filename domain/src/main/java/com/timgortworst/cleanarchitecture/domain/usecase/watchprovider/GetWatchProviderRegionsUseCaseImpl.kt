package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetWatchProviderRegionsUseCaseImpl @Inject constructor(
    private val repository: WatchProviderRepository,
) : GetWatchProviderRegionsUseCase {

    override fun execute(params: Unit) = repository.getWatchProviderRegions().mapNotNull {
            val result = (it.data ?: return@mapNotNull null)

            when (it) {
                is Result.Success -> Result.Success(result)
                is Result.Error -> Result.Error(it.error, result)
                is Result.Loading -> Result.Loading(result)
            }
        }
}
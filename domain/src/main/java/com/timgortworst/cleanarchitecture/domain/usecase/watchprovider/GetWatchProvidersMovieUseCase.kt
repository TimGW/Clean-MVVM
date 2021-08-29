package com.timgortworst.cleanarchitecture.domain.usecase.watchprovider

import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchProvidersMovieUseCase @Inject constructor(
    private val repository: WatchProviderRepository,
) : SuspendUseCase<GetWatchProvidersMovieUseCase.Params, @JvmSuppressWildcards Result<List<WatchProvider>>> {

    data class Params(val region: String)

    override suspend fun execute(params: Params) = repository.getWatchProvidersMovie(params.region)
}
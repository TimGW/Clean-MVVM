package com.timgortworst.cleanarchitecture.domain.usecase.tv

import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetTvShowDetailsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) : GetTvShowDetailsUseCase {

    data class Params(val movieId: Int, val countryCode: String?)

    override fun execute(params: Params): Flow<Result<TvShowDetails?>> {
        return tvShowRepository.getTvShowDetails(params.movieId).mapNotNull { response ->

            // filter null values & filter selected watch provider
            val result = response.data?.apply {
                watchProviders = watchProviders.filterKeys { it == params.countryCode }
            }

            return@mapNotNull when (response) {
                is Result.Success -> Result.Success(result)
                is Result.Error -> Result.Error(response.error, result)
                is Result.Loading -> Result.Loading(result)
            }
        }
    }
}
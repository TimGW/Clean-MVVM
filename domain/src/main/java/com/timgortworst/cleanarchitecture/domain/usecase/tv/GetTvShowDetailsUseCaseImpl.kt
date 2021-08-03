package com.timgortworst.cleanarchitecture.domain.usecase.tv

import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTvShowDetailsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) : GetTvShowDetailsUseCase {

    data class Params(val movieId: Int)

    override fun execute(params: Params): Flow<Result<TvShowDetails>> {
        return tvShowRepository.getTvShowDetails(params.movieId).map { response ->
            val result = response.data!!.first()
            when (response) {
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Success -> Result.Success(result)
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Error -> Result.Error(response.error, result)
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Loading -> Result.Loading(result)
            }
        }
    }
}
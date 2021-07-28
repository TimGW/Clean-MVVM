package com.timgortworst.cleanarchitecture.domain.usecase.tv

import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTvShowDetailsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) : GetTvShowDetailsUseCase {

    data class Params(val movieId: Int)

    override fun execute(params: Params): Flow<Resource<TvShowDetails>> {
        return tvShowRepository.getTvShowDetails(params.movieId).map { response ->
            when (response) {
                is Resource.Success -> Resource.Success(response.data.first())
                is Resource.Error -> Resource.Error(response.errorEntity)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}
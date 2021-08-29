package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieDetailsUseCase.Params, Flow<@JvmSuppressWildcards Result<MovieDetails?>>> {

    data class Params(val movieId: Int, val countryCode: String?)

    override fun execute(params: Params): Flow<Result<MovieDetails?>> {
        return movieRepository.getMovieDetailFlow(params.movieId).mapNotNull { response ->

            // filter selected watch provider
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
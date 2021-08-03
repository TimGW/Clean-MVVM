package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {

    data class Params(val movieId: Int)

    override fun execute(params: Params): Flow<Result<MovieDetails>> {
        return movieRepository.getMovieDetailFlow(params.movieId).map { response ->
            val result = response.data!!.first()
            when (response) {
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Success -> Result.Success(result)
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Error -> Result.Error(response.error, result)
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Loading -> Result.Loading(result)
            }
        }
    }
}
package com.timgortworst.cleanarchitecture.domain.usecase.moviedetail

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {

    data class Params(val movieId: Int)

    override fun execute(params: Params): Flow<Resource<MovieDetails>> {
        return movieRepository.getMovieDetailFlow(params.movieId).map { response ->
            when (response) {
                is Resource.Success -> Resource.Success(response.data.first())
                is Resource.Error -> Resource.Error(response.errorEntity)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}
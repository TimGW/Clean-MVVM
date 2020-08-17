package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.response.State
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository

class GetMovieDetailsUseCaseImpl(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {

    data class Params(val movieId: Int)

    override suspend fun execute(params: Params): State<MovieDetails> {
       return when (val result = movieRepository.getMovieDetails(params.movieId)) {
            is State.Success -> State.Success(result.data)
            is State.Error -> State.Error(result.errorEntity)
           State.Loading -> State.Loading
        }
    }
}

package com.timgortworst.cleanarchitecture.domain.usecase.moviedetail

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {

    data class Params(val movieId: Int)

    override suspend fun execute(params: Params): Resource<MovieDetails> {
       return when (val result = movieRepository.getMovieDetails(params.movieId)) {
            is Resource.Success -> Resource.Success(result.data)
            is Resource.Error -> Resource.Error(result.errorEntity)
           Resource.Loading -> Resource.Loading
        }
    }
}

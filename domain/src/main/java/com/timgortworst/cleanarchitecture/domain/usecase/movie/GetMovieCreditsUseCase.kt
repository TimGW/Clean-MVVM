package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<GetMovieCreditsUseCase.Params, Flow<@JvmSuppressWildcards Result<Credits>>> {

    data class Params(val movieId: Int)

    override fun execute(params: Params) = movieRepository.getMovieCredits(params.movieId)
}
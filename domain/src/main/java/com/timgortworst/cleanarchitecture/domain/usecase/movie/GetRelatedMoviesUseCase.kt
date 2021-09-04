package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRelatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<GetRelatedMoviesUseCase.Params, Flow<@JvmSuppressWildcards Result<List<Movie>>>> {

    data class Params(val movieId: Int)

    override fun execute(params: Params) = movieRepository.getRelatedMovies(params.movieId)
}
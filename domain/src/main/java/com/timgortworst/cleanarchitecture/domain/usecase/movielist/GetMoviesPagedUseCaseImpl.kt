package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesPagedUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
) : GetMoviesPagedUseCase {

    override fun execute(params: Unit) = movieRepository.getPagedMovieSource()
}
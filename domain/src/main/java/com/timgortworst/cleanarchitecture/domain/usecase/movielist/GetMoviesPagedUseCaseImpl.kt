package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import androidx.paging.filter
import androidx.paging.map
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesPagedUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
) : GetMoviesPagedUseCase {

    override fun execute(params: Unit) = movieRepository.getPagedMovies().map { pagingData ->
        pagingData.apply {
            filter { !it.adult }
            map {
                it.lowResImage = "https://image.tmdb.org/t/p/w185/".plus(it.posterPath)
                it.highResImage = "https://image.tmdb.org/t/p/original/".plus(it.posterPath)
            }
        }
    }
}
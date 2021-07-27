package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import androidx.paging.filter
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetMoviesPagedUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
) : GetMoviesPagedUseCase {

    override fun execute(params: Unit) = movieRepository.getPagedMovies().map { pagingData ->
        pagingData.filter { it.releaseDate?.before(Date()) == true }
    }
}
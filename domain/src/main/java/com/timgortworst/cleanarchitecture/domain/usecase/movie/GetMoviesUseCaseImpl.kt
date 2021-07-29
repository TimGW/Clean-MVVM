package com.timgortworst.cleanarchitecture.domain.usecase.movie

import androidx.paging.filter
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
) : GetMoviesUseCase {

    override fun execute(params: Unit) = movieRepository.getPagedMovies().map { pagingData ->
        pagingData.filter { it.releaseDate?.before(Date()) == true }
    }
}
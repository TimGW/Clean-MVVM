package com.timgortworst.cleanarchitecture.domain.usecase.movie

import androidx.paging.filter
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
) : GetMoviesUseCase {

    override fun execute(params: Unit) = movieRepository.getPagedMovies().map { pagingData ->
        pagingData.filter {
            val date = if (it.releaseDate.isNotBlank()) SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).parse(it.releaseDate) else null

            date?.before(Date()) == true
        }
    }
}
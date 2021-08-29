package com.timgortworst.cleanarchitecture.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.filter
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : UseCase<Unit, Flow<@JvmSuppressWildcards PagingData<Movie>>> {

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
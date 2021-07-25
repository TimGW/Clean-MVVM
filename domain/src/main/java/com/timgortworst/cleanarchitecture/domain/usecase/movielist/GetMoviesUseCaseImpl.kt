package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
) : GetMoviesUseCase {

    override suspend fun execute(params: Unit): Resource<List<Movie>> {
        return when (val response = movieRepository.getMovies()) {
            is Resource.Success -> {
                val mappedResult = response.data
                    .filterNot { it.adult }
                    .sortedBy { it.popularity }
                    .map {
                        it.lowResImage = "https://image.tmdb.org/t/p/w185/".plus(it.posterPath)
                        it.highResImage = "https://image.tmdb.org/t/p/original/".plus(it.posterPath)
                        it
                    }

                Resource.Success(mappedResult)
            }
            is Resource.Error -> Resource.Error(response.errorEntity)
            is Resource.Loading -> Resource.Loading
        }
    }
}
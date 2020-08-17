package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.model.state.State
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetMoviesUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val errorHandler: ErrorHandler
) : GetMoviesUseCase {

    override fun execute(params: Unit): Flow<State<List<Movie>>> {
        return movieRepository.getMoviesFlow().map { response ->
            when (response) {
                is State.Success -> {
                    val mappedResult = response.data
                            .filterNot { it.adult }
                            .sortedBy { it.popularity }

                    State.Success(mappedResult)
                }
                is State.Error -> State.Error(response.errorEntity)
                is State.Loading -> State.Loading
            }
        }.catch { cause ->
            emit(State.Error(errorHandler.getError(cause)))
        }
    }
}
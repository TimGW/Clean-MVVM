package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieCreditsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetRelatedMoviesUseCase
import com.timgortworst.cleanarchitecture.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    getRelatedMoviesUseCase: GetRelatedMoviesUseCase,
    getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val sharedPrefs: SharedPrefs,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val movieDetails: LiveData<Result<MovieDetails?>> =
        savedStateHandle.getLiveData<Int>(STATE_ID_MOVIE).switchMap { movieId ->
            getMovieDetailsUseCase.execute(
                GetMovieDetailsUseCase.Params(movieId, sharedPrefs.getWatchProviderRegion())
            ).map {
                it.error?.message = determineErrorMessage(it.error); it
            }.asLiveData()
        }

    val relatedMovies: Flow<Result<List<Movie>>> =
        getRelatedMoviesUseCase.execute(
            GetRelatedMoviesUseCase.Params(savedStateHandle.get<Int>(STATE_ID_MOVIE)!!)
        ).map {
            it.error?.message = determineErrorMessage(it.error); it
        }

    val movieCredits: Flow<Result<Credits>> =
        getMovieCreditsUseCase.execute(
            GetMovieCreditsUseCase.Params(savedStateHandle.get<Int>(STATE_ID_MOVIE)!!)
        ).map {
            it.error?.message = determineErrorMessage(it.error); it
        }

    private fun determineErrorMessage(error: Result.ErrorType?) = when (error) {
        is Result.ErrorType.DatabaseError -> R.string.database_error
        is Result.ErrorType.HttpError -> R.string.server_error
        is Result.ErrorType.IOError -> R.string.connection_error
        is Result.ErrorType.Unknown -> R.string.generic_error
        null -> null
    }

    companion object {
        private const val STATE_ID_MOVIE = "movieId"
    }
}
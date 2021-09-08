package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val sharedPrefs: SharedPrefs,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val movieDetails = savedStateHandle.getLiveData<Int>(STATE_ID_MOVIE).switchMap { movieId ->
        getMovieDetailsUseCase.execute(
            GetMovieDetailsUseCase.Params(movieId, sharedPrefs.getWatchProviderRegion())
        ).map {
            it.error?.message = determineErrorMessage(it.error); it
        }.asLiveData()
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
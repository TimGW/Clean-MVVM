package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<Int?>()
    val errorMessage: LiveData<Int?> = _errorMessage

    val movieDetails = savedStateHandle.getLiveData<Int>(STATE_ID_MOVIE).switchMap { movieId ->
        getMovieDetailsUseCase.execute(
            GetMovieDetailsUseCase.Params(movieId)
        ).onEach {
            _errorMessage.value = determineErrorMessage(it.error)
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
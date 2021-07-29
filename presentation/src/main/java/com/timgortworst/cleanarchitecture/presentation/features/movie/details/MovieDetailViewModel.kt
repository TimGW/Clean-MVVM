package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val movieDetails: LiveData<Resource<MovieDetails>> =
        savedStateHandle.getLiveData<Int>(STATE_ID_MOVIE).switchMap { movieId ->
           getMovieDetailsUseCase.execute(GetMovieDetailsUseCaseImpl.Params(movieId)).asLiveData()
        }

    fun setMovieId(movieId: Int) {
        savedStateHandle[STATE_ID_MOVIE] = movieId
    }

    companion object {
        private const val STATE_ID_MOVIE = "movieId"
    }
}
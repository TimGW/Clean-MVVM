package com.timgortworst.cleanarchitecture.presentation.features.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.State
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.presentation.extension.cancelIfActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private var moviesJob: Job? = null

    private val _movies = MutableLiveData<MovieDetails>()
    val movies: LiveData<MovieDetails>
        get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Unit>()
    val error: LiveData<Unit>
        get() = _error


    fun fetchMovieDetails(movieId: Int) {
        moviesJob.cancelIfActive()

        moviesJob = viewModelScope.launch {
            _loading.postValue(true)

            when (val res = getMovieDetailsUseCase.execute(GetMovieDetailsUseCaseImpl.Params(movieId))) {
                is State.Success -> {
                    _loading.postValue(false)
                    _movies.postValue(res.data)
                }
                is State.Error -> {
                    _loading.postValue(false)
                    _error.postValue(Unit)
                }
            }
        }
    }
}
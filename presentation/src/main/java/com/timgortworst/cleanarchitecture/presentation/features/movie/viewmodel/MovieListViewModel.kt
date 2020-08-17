package com.timgortworst.cleanarchitecture.presentation.features.movie.viewmodel

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.response.State
import com.timgortworst.cleanarchitecture.domain.usecase.movie.*
import com.timgortworst.cleanarchitecture.presentation.extension.cancelIfActive
import com.timgortworst.cleanarchitecture.presentation.model.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private var moviesJob: Job? = null

    private val _movies = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    fun fetchMovies() {
        moviesJob.cancelIfActive()

        moviesJob = viewModelScope.launch {
            getMoviesUseCase.execute(Unit).collect { res ->
                when (res) {
                    State.Loading -> _loading.value = true
                    is State.Success -> {
                        _loading.value = false
                        _movies.postValue(res.data)
                    }
                    is State.Error -> {
                        _loading.value = false
                        _error.value = Event("Check your internet")
                    }
                }
            }
        }
    }
}
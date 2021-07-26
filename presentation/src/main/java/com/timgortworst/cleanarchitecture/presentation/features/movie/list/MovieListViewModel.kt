package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getMoviesPagedUseCase: GetMoviesPagedUseCase,
) : ViewModel() {

    val moviesPaged = getMoviesPagedUseCase
        .execute(Unit)
        .cachedIn(viewModelScope)
}
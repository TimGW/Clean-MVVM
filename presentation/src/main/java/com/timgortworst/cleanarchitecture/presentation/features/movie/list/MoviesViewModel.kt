package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    val moviesPaged = getMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
}
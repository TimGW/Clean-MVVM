package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
   getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    val movies = getMoviesUseCase.execute(Unit).asLiveData()
}
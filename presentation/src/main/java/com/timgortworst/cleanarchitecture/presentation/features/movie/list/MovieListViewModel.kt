package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getMoviesPagedUseCase: GetMoviesPagedUseCase,
) : ViewModel() {

    val moviesPaged = Pager(
        PagingConfig(pageSize = 100)
    ) {
        getMoviesPagedUseCase.execute(Unit)
    }.flow.map { pagingData ->
        pagingData.apply {
            filter { !it.adult }
            map {
                it.lowResImage = "https://image.tmdb.org/t/p/w185/".plus(it.posterPath)
                it.highResImage = "https://image.tmdb.org/t/p/original/".plus(it.posterPath)
            }
        }
    }.cachedIn(viewModelScope)
}
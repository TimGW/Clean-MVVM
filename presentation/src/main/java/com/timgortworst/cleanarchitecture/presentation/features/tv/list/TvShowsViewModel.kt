package com.timgortworst.cleanarchitecture.presentation.features.tv.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    getTvShowsUseCase: GetTvShowsUseCase,
) : ViewModel() {

    val tvShowsPaged = getTvShowsUseCase.execute(Unit).cachedIn(viewModelScope)
}
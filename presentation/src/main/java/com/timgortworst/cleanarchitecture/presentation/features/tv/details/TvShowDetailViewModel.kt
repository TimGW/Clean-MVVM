package com.timgortworst.cleanarchitecture.presentation.features.tv.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowDetailsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tvShowDetails: LiveData<Resource<TvShowDetails>> =
        savedStateHandle.getLiveData<Int>(STATE_ID_TV_SHOW).switchMap { tvShowId ->
            getTvShowDetailsUseCase.execute(GetTvShowDetailsUseCaseImpl.Params(tvShowId))
                .asLiveData()
        }

    fun setTvShowId(tvShowId: Int) {
        savedStateHandle[STATE_ID_TV_SHOW] = tvShowId
    }

    companion object {
        private const val STATE_ID_TV_SHOW = "tvShowId"
    }
}
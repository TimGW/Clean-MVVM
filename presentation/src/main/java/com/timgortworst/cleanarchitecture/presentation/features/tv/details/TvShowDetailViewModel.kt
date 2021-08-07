package com.timgortworst.cleanarchitecture.presentation.features.tv.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val sharedPrefs: SharedPrefs,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tvShowDetails: LiveData<Result<TvShowDetails?>> =
        savedStateHandle.getLiveData<Int>(STATE_ID_TV_SHOW).switchMap { tvShowId ->
            getTvShowDetailsUseCase.execute(
                GetTvShowDetailsUseCaseImpl.Params(tvShowId, sharedPrefs.getWatchProviderRegion())
            ).map {
                it.error?.message = determineErrorMessage(it.error); it
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
        private const val STATE_ID_TV_SHOW = "tvShowId"
    }
}
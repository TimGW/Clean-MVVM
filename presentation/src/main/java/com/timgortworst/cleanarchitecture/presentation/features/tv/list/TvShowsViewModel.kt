package com.timgortworst.cleanarchitecture.presentation.features.tv.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersTvUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersTvUseCaseImpl
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    getTvShowsUseCase: GetTvShowsUseCase,
    private val getWatchProvidersTvUseCase: GetWatchProvidersTvUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    val tvShowsPaged = getTvShowsUseCase.execute(Unit).cachedIn(viewModelScope)
    val region = sharedPrefs.getWatchProviderRegion().orEmpty()
    val watchProviders = liveData {
        val watchProviders = getWatchProvidersTvUseCase.execute(
            GetWatchProvidersTvUseCaseImpl.Params(region)
        )

        watchProviders.error?.message = determineErrorMessage(watchProviders.error)

        emit(watchProviders)
    }

    fun setSelectedProviders(
        providers: MutableList<WatchProvidersAdapter.ViewItem>,
    ) {
        val checkedProviders = providers
            .filter { it.isChecked }
            .map { it.watchProvider }

        sharedPrefs.setWatchProvidersTv(checkedProviders)
    }

    fun isProviderChecked(
        providerId: Int,
    ): Boolean {
        return sharedPrefs.getWatchProvidersTv()?.any { wp ->
            wp.providerId == providerId
        } ?: false
    }

    private fun determineErrorMessage(error: Result.ErrorType?) = when (error) {
        is Result.ErrorType.DatabaseError -> R.string.database_error
        is Result.ErrorType.HttpError -> R.string.server_error
        is Result.ErrorType.IOError -> R.string.connection_error
        is Result.ErrorType.Unknown -> R.string.generic_error
        null -> null
    }
}
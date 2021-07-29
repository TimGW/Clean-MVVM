package com.timgortworst.cleanarchitecture.presentation.features.tv.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersTvUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersTvUseCaseImpl
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

    private val providerRegion = MutableLiveData<String>()
    val watchProviders = Transformations.switchMap(providerRegion) { region ->
        liveData {
            emit(getWatchProvidersTvUseCase.execute(GetWatchProvidersTvUseCaseImpl.Params(region)))
        }
    }

    fun getAllProviders() {
        providerRegion.value = sharedPrefs.getWatchProviderRegion().orEmpty()
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
}
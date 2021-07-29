package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMoviesUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersMovieUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersMovieUseCaseImpl
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    private val getWatchProvidersMovieUseCase: GetWatchProvidersMovieUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    val moviesPaged = getMoviesUseCase.execute(Unit).cachedIn(viewModelScope)

    private val providerRegion = MutableLiveData<String>()
    val watchProviders = Transformations.switchMap(providerRegion) { region ->
        liveData {
            emit(
                getWatchProvidersMovieUseCase.execute(
                    GetWatchProvidersMovieUseCaseImpl.Params(region)
                )
            )
        }
    }

    fun getAllProviders() {
        providerRegion.value = sharedPrefs.getWatchProviderRegion().orEmpty()
    }

    fun setSelectedProviders(
        providers: MutableList<WatchProvidersAdapter.ViewItem>
    ) {
        val checkedProviders = providers
            .filter { it.isChecked }
            .map { it.watchProvider }

        sharedPrefs.setWatchProvidersMovie(checkedProviders)
    }

    fun isProviderChecked(
        providerId: Int,
    ): Boolean {
        return sharedPrefs.getWatchProvidersMovie()?.any { wp ->
            wp.providerId == providerId
        } ?: false
    }
}
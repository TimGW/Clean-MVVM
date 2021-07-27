package com.timgortworst.cleanarchitecture.presentation.features.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProvider
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderMovieUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderMovieUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase,
    private val getWatchProviderMovieUseCase: GetWatchProviderMovieUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    private val load = MutableLiveData<Unit>()
    val regions = Transformations.switchMap(load) {
        liveData {
            emit(getWatchProviderRegionsUseCase.execute(it))
        }
    }

    private val iso = MutableLiveData<String>()
    val movieProviders = Transformations.switchMap(iso) {
        liveData {
            emit(getWatchProviderMovieUseCase.execute(GetWatchProviderMovieUseCaseImpl.Params(it)))
        }
    }

    init {
        load.value = Unit
    }

    fun updateMovieProviders(isoValue: String) {
        sharedPrefs.setWatchProviderRegion(isoValue)
        iso.value = isoValue
    }

    fun setOnboardingDone(isDone: Boolean) {
        sharedPrefs.setOnboardingDone(isDone)
    }

    fun setWatchProviders(providers: List<WatchProvider>) {
        sharedPrefs.setWatchProviders(providers)
    }
}
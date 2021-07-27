package com.timgortworst.cleanarchitecture.presentation.features.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProvider
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase,
    private val getWatchProvidersMovieUseCase: GetWatchProvidersMovieUseCase,
    private val getWatchProvidersTvUseCase: GetWatchProvidersTvUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    private val load = MutableLiveData<Unit>()
    val regions = Transformations.switchMap(load) {
        liveData {
            emit(getWatchProviderRegionsUseCase.execute(it))
        }
    }

    private val isoMovie = MutableLiveData<String>()
    val movieProviders = Transformations.switchMap(isoMovie) {
        liveData {
            emit(getWatchProvidersMovieUseCase.execute(GetWatchProvidersMovieUseCaseImpl.Params(it)))
        }
    }

    private val isoTv = MutableLiveData<String>()
    val tvProviders = Transformations.switchMap(isoTv) {
        liveData {
            emit(getWatchProvidersTvUseCase.execute(GetWatchProvidersTvUseCaseImpl.Params(it)))
        }
    }

    init {
        load.value = Unit
    }

    fun updateProviders(isoValue: String) {
        sharedPrefs.setWatchProviderRegion(isoValue)
        isoMovie.value = isoValue
        isoTv.value = isoValue
    }

    fun setWatchProvidersMovie(providers: List<WatchProvider>) {
        sharedPrefs.setWatchProvidersMovie(providers)
    }

    fun setWatchProvidersTv(providers: List<WatchProvider>) {
        sharedPrefs.setWatchProvidersTv(providers)
    }
}
package com.timgortworst.cleanarchitecture.presentation.features.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    private val load = MutableLiveData<Unit>()
    val regions = Transformations.switchMap(load) {
        liveData {
            emit(getWatchProviderRegionsUseCase.execute(it))
        }
    }

    init {
        load.value = Unit
    }

    fun updateProviders(isoValue: String) {
        sharedPrefs.setWatchProviderRegion(isoValue)
    }
}
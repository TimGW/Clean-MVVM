package com.timgortworst.cleanarchitecture.presentation.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    val regions = getWatchProviderRegionsUseCase.execute(Unit).asLiveData()

    fun updateProviders(isoValue: String) {
        sharedPrefs.setWatchProviderRegion(isoValue)
    }
}
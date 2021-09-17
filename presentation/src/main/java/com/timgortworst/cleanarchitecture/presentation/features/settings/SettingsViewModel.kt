package com.timgortworst.cleanarchitecture.presentation.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import com.timgortworst.cleanarchitecture.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase,
) : ViewModel() {
    private val _errorMessage = MutableLiveData<Int?>()
    val errorMessage: LiveData<Int?> = _errorMessage

    private val _data = MutableLiveData<List<WatchProviderRegion>?>()
    val data: LiveData<List<WatchProviderRegion>?> = _data

    init {
        viewModelScope.launch {
            getWatchProviderRegionsUseCase.execute(Unit).collectLatest {
                _data.value = it.data
                _errorMessage.value = when (it.error) {
                    null -> null
                    else -> R.string.region_error
                }
            }
        }
    }
}
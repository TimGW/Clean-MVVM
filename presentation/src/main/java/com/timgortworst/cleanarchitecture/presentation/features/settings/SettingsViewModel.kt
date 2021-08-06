package com.timgortworst.cleanarchitecture.presentation.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
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
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<Int?>()
    val errorMessage: LiveData<Int?>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _data = MutableLiveData<List<WatchProviderRegion>?>()
    val data: LiveData<List<WatchProviderRegion>?>
        get() = _data

    init {
        viewModelScope.launch {
            getWatchProviderRegionsUseCase.execute(Unit).collectLatest {
                _isLoading.value = it is Result.Loading
                _data.value = it.data
                _errorMessage.value = when (it.error) {
                    is Result.ErrorType.DatabaseError -> R.string.database_error
                    is Result.ErrorType.HttpError -> R.string.server_error
                    is Result.ErrorType.IOError -> R.string.connection_error
                    is Result.ErrorType.Unknown -> R.string.generic_error
                    null -> null
                }
            }
        }
    }

    fun updateProviders(isoValue: String) {
        sharedPrefs.setWatchProviderRegion(isoValue)
    }
}
package com.timgortworst.cleanarchitecture.presentation.features.welcome

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.domain.model.movie.WatchProviderRegion
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    getWatchProviderRegionsUseCase: GetWatchProviderRegionsUseCase
) : ViewModel() {
    private val load = MutableLiveData<Unit>()
    val regions: LiveData<Resource<List<WatchProviderRegion>>> = Transformations.switchMap(load) {
        liveData { emit(getWatchProviderRegionsUseCase.execute(Unit)) }
    }

    init {
        load.value = Unit
    }
}
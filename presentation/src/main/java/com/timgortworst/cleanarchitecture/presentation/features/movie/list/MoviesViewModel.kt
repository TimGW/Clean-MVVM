package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMoviesUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersMovieUseCase
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    private val getWatchProvidersMovieUseCase: GetWatchProvidersMovieUseCase,
    private val sharedPrefs: SharedPrefs,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<Int?>()
    val errorMessage: LiveData<Int?> = _errorMessage

    val moviesPaged = getMoviesUseCase.execute(Unit).cachedIn(viewModelScope)
    private val region = sharedPrefs.getWatchProviderRegion().orEmpty()
    val watchProviders = liveData {
        val watchProviders = getWatchProvidersMovieUseCase.execute(
            GetWatchProvidersMovieUseCase.Params(region)
        )

        _errorMessage.value = determineErrorMessage(watchProviders.error)

        emit(watchProviders)
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

    private fun determineErrorMessage(error: Result.ErrorType?) = when (error) {
        is Result.ErrorType.DatabaseError -> R.string.database_error
        is Result.ErrorType.HttpError -> R.string.server_error
        is Result.ErrorType.IOError -> R.string.connection_error
        is Result.ErrorType.Unknown -> R.string.generic_error
        null -> null
    }
}
package com.timgortworst.cleanarchitecture.presentation.features.watchprovider

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.MoviesViewModel
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieWatchProvidersDialog : BaseWatchProvidersDialog() {
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()

        viewModel.getAllProviders()
    }

    // FIXME: nullability
    private fun observeData() {
        viewModel.watchProviders.observe(this) {
            when (it) {
                is Resource.Error -> { }
                is Resource.Loading -> { } // FIXME empty state
                is Resource.Success -> {
                    watchProviderAdapter.submitList(it.data!!.map { watchProvider ->
                        WatchProvidersAdapter.ViewItem(
                            watchProvider,
                            viewModel.isProviderChecked(watchProvider.providerId)
                        ).apply {
                            watchProviderAdapter.onCheckedListener = { _, _ ->
                                viewModel.setSelectedProviders(
                                    watchProviderAdapter.currentList,
                                )
                            }
                        }
                    })
                }
            }
        }
    }

    companion object {
        const val TAG = "SettingsWatchProvidersDialog"

        fun newInstance() = MovieWatchProvidersDialog()
    }
}

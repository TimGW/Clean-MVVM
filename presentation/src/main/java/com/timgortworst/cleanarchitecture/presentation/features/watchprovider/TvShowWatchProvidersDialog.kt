package com.timgortworst.cleanarchitecture.presentation.features.watchprovider

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import com.timgortworst.cleanarchitecture.presentation.features.tv.list.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowWatchProvidersDialog : BaseWatchProvidersDialog() {
    private val viewModel by viewModels<TvShowsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()

        viewModel.getAllProviders()
    }

    private fun observeData() {
        viewModel.watchProviders.observe(this) {
            when (it) {
                is Resource.Error -> { }
                Resource.Loading -> { }
                is Resource.Success -> {
                    watchProviderAdapter.submitList(it.data.map { watchProvider ->
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

        fun newInstance() = TvShowWatchProvidersDialog()
    }
}
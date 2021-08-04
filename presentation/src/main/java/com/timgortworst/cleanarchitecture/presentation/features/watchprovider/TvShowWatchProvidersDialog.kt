package com.timgortworst.cleanarchitecture.presentation.features.watchprovider

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.presentation.R
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
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(getString(R.string.connection_error)) // todo set correct error
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val data = it.data?.map {
                        WatchProvidersAdapter.ViewItem(
                            it,
                            viewModel.isProviderChecked(it.providerId)
                        )
                    }

                    with(watchProviderAdapter) {
                        onCheckedListener = { _, _ ->
                            viewModel.setSelectedProviders(watchProviderAdapter.currentList)
                        }
                        submitList(data)
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "SettingsWatchProvidersDialog"

        fun newInstance() = TvShowWatchProvidersDialog()
    }
}

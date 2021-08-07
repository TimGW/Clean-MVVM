package com.timgortworst.cleanarchitecture.presentation.features.watchprovider

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import com.timgortworst.cleanarchitecture.presentation.features.tv.list.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowWatchProvidersDialog : BaseWatchProvidersDialog() {
    private val viewModel by viewModels<TvShowsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    private fun observeData() {
        viewModel.watchProviders.observe(this) { result ->
            binding.progress.visibility = if (result is Result.Loading) View.VISIBLE else View.INVISIBLE
            result.data?.let { showData(it) }
            result.error?.message?.let { showError(getString(it)) } ?: run {
                binding.errorMessage.visibility = View.GONE
            }
        }
    }

    private fun showData(list: List<WatchProvider>) {
        val data = list.map {
            WatchProvidersAdapter.ViewItem(it, viewModel.isProviderChecked(it.providerId))
        }

        with(watchProviderAdapter) {
            onCheckedListener = { _, _ ->
                viewModel.setSelectedProviders(watchProviderAdapter.currentList)
            }
            submitList(data)
        }
    }

    companion object {
        const val TAG = "SettingsWatchProvidersDialog"

        fun newInstance() = TvShowWatchProvidersDialog()
    }
}

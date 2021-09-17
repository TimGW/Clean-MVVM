package com.timgortworst.cleanarchitecture.presentation.features.watchprovider

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProvider
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.MoviesViewModel
import com.timgortworst.cleanarchitecture.presentation.features.settings.WatchProvidersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieWatchProvidersDialog : BaseWatchProvidersDialog() {
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    private fun observeData() {
        viewModel.watchProviders.observe(this) { result ->
            binding.progress.visibility = if (result is Result.Loading) View.VISIBLE else View.INVISIBLE
            result.data?.let { showData(it) }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let { showError(getString(it)) } ?: run {
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

        fun newInstance() = MovieWatchProvidersDialog()
    }
}

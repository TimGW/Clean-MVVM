package com.timgortworst.cleanarchitecture.presentation.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel by viewModels<SettingsViewModel>()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val movieProviderAdapter by lazy {
        WatchProvidersAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.regions.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> { }
                Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.regionSpinner.adapter = WatchProviderRegionAdapter(
                        it.data, binding.regionSpinner
                    ).apply {
                        selectedListener = { viewModel.updateMovieProviders(it.iso) }
                    }
                }
            }
        }

        viewModel.movieProviders.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                }
                Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.watchProviderRv.adapter = movieProviderAdapter.apply {
                        submitList(it.data.map { watchProvider ->

                            val isChecked = viewModel.checkedProviders.value?.any { wp ->
                                wp.providerId == watchProvider.providerId
                            } ?: false

                            WatchProvidersAdapter.ViewItem(watchProvider, isChecked)
                        })
                        onCheckedListener = { watchProvider, isChecked ->
                            val providers = movieProviderAdapter.currentList
                                .filter { it.isChecked }
                                .map { it.watchProvider }
                            viewModel.setWatchProviders(providers)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

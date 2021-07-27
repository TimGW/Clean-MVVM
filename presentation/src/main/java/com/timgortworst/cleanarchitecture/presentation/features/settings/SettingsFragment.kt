package com.timgortworst.cleanarchitecture.presentation.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel by viewModels<SettingsViewModel>()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val movieProviderAdapter by lazy { WatchProvidersAdapter() }
    private val tvProviderAdapter by lazy { WatchProvidersAdapter() }

    @Inject
    lateinit var sharedPrefs: SharedPrefs

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
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        viewModel.regions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                }
                Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.regionSpinner.adapter = WatchProviderRegionAdapter(
                        resource.data, binding.regionSpinner,
                    ).apply {
                        selectedListener = {
                            viewModel.updateProviders(it.iso)
                        }
                    }
                    binding.regionSpinner.setSelection(
                        resource.data.indexOfFirst {
                            it.iso == sharedPrefs.getWatchProviderRegion()
                        })
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
                    binding.watchProviderRvMovie.adapter = movieProviderAdapter.apply {
                        submitList(it.data.map { watchProvider ->

                            val isChecked = sharedPrefs.getWatchProvidersMovie()?.any { wp ->
                                wp.providerId == watchProvider.providerId
                            } ?: false

                            WatchProvidersAdapter.ViewItem(watchProvider, isChecked).apply {
                                onCheckedListener = { _, _ ->
                                    val providers = movieProviderAdapter.currentList
                                        .filter { it.isChecked }
                                        .map { it.watchProvider }

                                    viewModel.setWatchProvidersMovie(providers)
                                }
                            }
                        })
                    }
                }
            }
        }

        viewModel.tvProviders.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                }
                Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.watchProviderRvTv.adapter = tvProviderAdapter.apply {
                        submitList(it.data.map { watchProvider ->
                            val isChecked = sharedPrefs.getWatchProvidersTv()?.any { wp ->
                                wp.providerId == watchProvider.providerId
                            } ?: false

                            WatchProvidersAdapter.ViewItem(watchProvider, isChecked).apply {
                                onCheckedListener = { _, _ ->
                                    val providers = tvProviderAdapter.currentList
                                        .filter { it.isChecked }
                                        .map { it.watchProvider }

                                    viewModel.setWatchProvidersTv(providers)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

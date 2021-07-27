package com.timgortworst.cleanarchitecture.presentation.features.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private val viewModel by viewModels<WelcomeViewModel>()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val movieProviderAdapter by lazy {
        WatchProvidersAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
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
                            WatchProvidersAdapter.ViewItem(watchProvider, false)
                        }.distinctBy { item -> item.watchProvider.providerName })
                        onCheckedListener = { watchProvider, isChecked -> }
                    }
                }
            }
        }

        binding.buttonDone.setOnClickListener {
            val providers = movieProviderAdapter.currentList
                .filter { it.isChecked }
                .map { it.watchProvider }

            viewModel.setOnboardingDone(true)
            viewModel.setWatchProviders(providers)

            requireActivity().finish()
            findNavController().navigate(R.id.MovieActivity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

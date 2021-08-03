package com.timgortworst.cleanarchitecture.presentation.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel by viewModels<SettingsViewModel>()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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

        setupToolbar()
        observeData()
    }

    // FIXME
//    override fun onResume() {
//        super.onResume()
//        (requireActivity() as? MainActivity)?.setExpandedAppBar(false)
//    }
//
//    override fun onPause() {
//        super.onPause()
//
//        (requireActivity() as? MainActivity)?.setExpandedAppBar(true)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.layoutToolbar.toolbar)
        NavigationUI.setupWithNavController(
            binding.layoutToolbar.collapsingToolbarLayout,
            binding.layoutToolbar.toolbar,
            findNavController(),
            AppBarConfiguration.Builder(R.id.page_movies, R.id.page_tv, R.id.page_settings).build()
        )
    }

    // TODO fix nullability
    private fun observeData() {
        viewModel.regions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Error -> { } // FIXME
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Loading -> { }
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Success -> {
                    binding.regionSpinner.adapter = WatchProviderRegionAdapter(
                        resource.data!!, binding.regionSpinner,
                    ).apply {
                        selectedListener = {
                            viewModel.updateProviders(it.iso)
                        }
                    }
                    binding.regionSpinner.setSelection(
                        resource.data!!.indexOfFirst {
                            it.iso == sharedPrefs.getWatchProviderRegion()
                        })
                }
            }
        }
    }
}

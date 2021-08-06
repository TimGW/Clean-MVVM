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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentSettingsBinding
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.MainActivity
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

    private fun observeData() {
        viewModel.regions.observe(viewLifecycleOwner) { result ->
            binding.progress.visibility = if (result is Result.Loading) View.VISIBLE else View.INVISIBLE
            result.data?.let { showData(it) }
            result.error?.let { showError(getString(R.string.generic_error)) } // todo set correct error
        }
    }

    private fun showData(list: List<WatchProviderRegion>) {
        binding.regionSpinner.adapter = WatchProviderRegionAdapter(
            list, binding.regionSpinner,
        ).apply {
            selectedListener = {
                viewModel.updateProviders(it.iso)
            }
        }
        binding.regionSpinner.setSelection(
            list.indexOfFirst {
                it.iso == sharedPrefs.getWatchProviderRegion()
            })
    }

    private fun showError(message: String) {
        val bottomNavView = (requireActivity() as? MainActivity)
            ?.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        view?.snackbar(
            message = message, // todo show correct error
            anchorView = bottomNavView
        )
    }
}

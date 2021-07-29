package com.timgortworst.cleanarchitecture.presentation.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.ActivityMainBinding
import com.timgortworst.cleanarchitecture.presentation.extension.animateSlideFade
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentDestinationId = INVALID_ID

    companion object {
        fun intentBuilder(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

        const val INVALID_ID = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        currentDestinationId = navHostFragment.navController.currentDestination?.id ?: INVALID_ID

        // connect bottomNavigationView
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            val duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()

            if (isNavigatingToDetails(destination.id)) {
                binding.bottomNavigation.animateSlideFade(duration, View.GONE)
            } else if (isNavigatingToList(destination.id)) {
                binding.bottomNavigation.animateSlideFade(duration, View.VISIBLE)
            }
            currentDestinationId = destination.id
        }
    }

    private fun isNavigatingToDetails(destinationId: Int) =
        (currentDestinationId == R.id.page_movies && destinationId == R.id.fragmentMovieDetails) ||
        (currentDestinationId == R.id.page_tv && destinationId == R.id.tvShowDetailsFragment)

    private fun isNavigatingToList(destinationId: Int) =
        (currentDestinationId == R.id.fragmentMovieDetails && destinationId == R.id.page_movies) ||
        (currentDestinationId == R.id.tvShowDetailsFragment && destinationId == R.id.page_tv)
}

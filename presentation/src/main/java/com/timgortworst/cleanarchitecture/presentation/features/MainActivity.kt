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
import com.timgortworst.cleanarchitecture.presentation.features.base.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var themeHelper: ThemeHelper

    companion object {
        fun intentBuilder(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(themeHelper.getAppTheme())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // connect bottomNavigationView
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.page_movies,
                R.id.page_settings -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() { binding.bottomNavigation.visibility = View.VISIBLE }

    private fun hideBottomNav() { binding.bottomNavigation.visibility = View.GONE }
}

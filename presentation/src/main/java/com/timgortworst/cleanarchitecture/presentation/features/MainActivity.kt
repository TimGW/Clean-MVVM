package com.timgortworst.cleanarchitecture.presentation.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        fun intentBuilder(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

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

        // connect collapsingToolbar
        NavigationUI.setupWithNavController(
            binding.collapsingToolbarLayout,
            binding.toolbar,
            navHostFragment.navController,
            AppBarConfiguration.Builder(R.id.page_movies, R.id.page_tv).build()
        )
    }

    fun setExpandedAppBar(expanded: Boolean) {
        binding.appbar.setExpanded(expanded)
    }
}

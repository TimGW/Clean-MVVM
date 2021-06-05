package com.timgortworst.cleanarchitecture.presentation.features.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.ActivityMovieBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMovieBinding

    companion object {
        fun intentBuilder(context: Context): Intent {
            return Intent(context, MovieActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navController = (navHostFragment as NavHostFragment).navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentMovieList -> setTranslucentStatus(false)
                R.id.fragmentMovieDetails -> setTranslucentStatus(true)
            }
        }
    }
}

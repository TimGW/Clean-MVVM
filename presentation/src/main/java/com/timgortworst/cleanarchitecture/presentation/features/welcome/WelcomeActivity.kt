package com.timgortworst.cleanarchitecture.presentation.features.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timgortworst.cleanarchitecture.presentation.databinding.ActivityMovieBinding
import com.timgortworst.cleanarchitecture.presentation.databinding.ActivityWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    companion object {
        fun intentBuilder(context: Context): Intent {
            return Intent(context, WelcomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityWelcomeBinding.inflate(layoutInflater).root)
    }
}

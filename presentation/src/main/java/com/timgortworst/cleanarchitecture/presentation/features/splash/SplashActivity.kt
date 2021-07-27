package com.timgortworst.cleanarchitecture.presentation.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MyTheme_NoActionBar_Launcher)
        super.onCreate(savedInstanceState)

        startActivity(MovieActivity.intentBuilder(this))
        finish()
    }
}

package com.timgortworst.cleanarchitecture.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.timgortworst.cleanarchitecture.presentation.features.settings.ThemeHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CleanApp : Application() {

    @Inject
    lateinit var themeHelper: ThemeHelper

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(themeHelper.getNightMode())
    }
}

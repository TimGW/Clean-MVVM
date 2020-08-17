package com.timgortworst.cleanarchitecture.presentation

import android.app.Application
import com.timgortworst.cleanarchitecture.data.di.*
import com.timgortworst.cleanarchitecture.domain.di.useCaseModule
import com.timgortworst.cleanarchitecture.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class CleanApp : Application() {
    private val appComponent: List<Module> = listOf(
        appModule,
        networkModule,
        repositoryModule,
        useCaseModule,
        viewModelModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            androidContext(this@CleanApp)
            modules(appComponent)
        }
    }

    companion object {
        const val TAG = "TasqsApp"
    }
}

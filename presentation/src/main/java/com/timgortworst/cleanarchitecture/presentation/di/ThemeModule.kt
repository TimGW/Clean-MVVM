package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.presentation.features.base.ThemeHelper
import com.timgortworst.cleanarchitecture.presentation.features.base.ThemeHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemeModule {

    @Binds
    abstract fun provideThemeHelper(
        themeHelperImpl: ThemeHelperImpl
    ): ThemeHelper
}
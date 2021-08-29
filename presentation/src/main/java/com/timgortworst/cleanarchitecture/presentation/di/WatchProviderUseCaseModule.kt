package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProviderRegionsUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersMovieUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.GetWatchProvidersMovieUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class WatchProviderUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetWatchProviderRegionsUseCase(
        getWatchProviderRegionsUseCaseImpl: GetWatchProviderRegionsUseCaseImpl
    ): GetWatchProviderRegionsUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetWatchProviderMovieUseCase(
        getWatchProvidersMovieUseCase: GetWatchProvidersMovieUseCaseImpl
    ): GetWatchProvidersMovieUseCase
}
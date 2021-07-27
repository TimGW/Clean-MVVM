package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesPagedUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesPagedUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.watchprovider.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetMoviesPagedUseCase(
        getMoviesPagedUseCaseImpl: GetMoviesPagedUseCaseImpl
    ): GetMoviesPagedUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetMovieDetailsUseCase(
        getMovieDetailsUseCaseImpl: GetMovieDetailsUseCaseImpl
    ): GetMovieDetailsUseCase

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

    @Binds
    @ViewModelScoped
    abstract fun provideGetWatchProviderTvUseCase(
        getWatchProvidersTvUseCase: GetWatchProvidersTvUseCaseImpl
    ): GetWatchProvidersTvUseCase
}
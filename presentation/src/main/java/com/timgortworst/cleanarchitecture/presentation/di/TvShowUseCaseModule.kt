package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.tv.GetTvShowsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class TvShowUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetTvShowsUseCase(
        getTvShowsUseCaseImpl: GetTvShowsUseCaseImpl
    ): GetTvShowsUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetTvShowDetailsUseCase(
        getTvShowDetailsUseCaseImpl: GetTvShowDetailsUseCaseImpl
    ): GetTvShowDetailsUseCase
}
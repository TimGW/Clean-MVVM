package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMoviesUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetMoviesPagedUseCase(
        getMoviesUseCaseImpl: GetMoviesUseCaseImpl
    ): GetMoviesUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetMovieDetailsUseCase(
        getMovieDetailsUseCaseImpl: GetMovieDetailsUseCaseImpl
    ): GetMovieDetailsUseCase
}
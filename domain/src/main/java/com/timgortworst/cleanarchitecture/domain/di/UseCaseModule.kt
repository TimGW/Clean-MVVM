package com.timgortworst.cleanarchitecture.domain.di

import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetMoviesUseCase(
        movieRepository: MovieRepository,
        errorHandler: ErrorHandler
    ): GetMoviesUseCase {
        return GetMoviesUseCaseImpl(movieRepository, errorHandler)
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailsUseCase(
        movieRepository: MovieRepository
    ): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCaseImpl(movieRepository)
    }
}
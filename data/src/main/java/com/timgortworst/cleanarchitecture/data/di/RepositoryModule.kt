package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.repository.MovieRepositoryImpl
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for all repositories
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}

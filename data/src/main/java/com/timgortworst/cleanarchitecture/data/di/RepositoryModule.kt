package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.database.LocalDataSourceMovie
import com.timgortworst.cleanarchitecture.data.network.RemoteDataSourceMovie
import com.timgortworst.cleanarchitecture.data.repository.MovieRepositoryImpl
import com.timgortworst.cleanarchitecture.domain.model.state.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for all repositories
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteDataSourceMovie: RemoteDataSourceMovie,
        localDataSourceMovie: LocalDataSourceMovie,
        errorHandler: ErrorHandler
    ): MovieRepository {
        return MovieRepositoryImpl(remoteDataSourceMovie, localDataSourceMovie, errorHandler)
    }
}

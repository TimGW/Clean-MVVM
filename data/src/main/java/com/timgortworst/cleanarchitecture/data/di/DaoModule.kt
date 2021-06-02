package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.database.AppDatabase
import com.timgortworst.cleanarchitecture.data.database.LocalDataSourceMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module for all local data access objects which are stored in Room
 */
@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMovieDao(database: AppDatabase): LocalDataSourceMovie {
        return database.movieDao()
    }
}
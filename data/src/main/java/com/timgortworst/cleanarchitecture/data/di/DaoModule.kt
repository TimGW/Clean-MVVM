package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.local.AppDatabase
import com.timgortworst.cleanarchitecture.data.local.MovieDao
import com.timgortworst.cleanarchitecture.data.local.TvShowDao
import com.timgortworst.cleanarchitecture.data.local.WatchProviderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideTvShowDao(database: AppDatabase): TvShowDao {
        return database.tvShowDao()
    }

    @Provides
    fun provideWatchProviderRegionsDao(database: AppDatabase): WatchProviderDao {
        return database.watchProviderDao()
    }
}
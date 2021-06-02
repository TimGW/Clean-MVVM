package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.network.RemoteDataSourceMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Module for all remote api endpoints
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideRemoteDataSourceMovie(retrofit: Retrofit): RemoteDataSourceMovie {
        return retrofit.create(RemoteDataSourceMovie::class.java)
    }
}
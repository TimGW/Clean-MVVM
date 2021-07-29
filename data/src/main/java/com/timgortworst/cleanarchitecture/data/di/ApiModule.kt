package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.remote.MovieService
import com.timgortworst.cleanarchitecture.data.remote.TvShowService
import com.timgortworst.cleanarchitecture.data.remote.WatchProviderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    fun provideTvShowService(retrofit: Retrofit): TvShowService {
        return retrofit.create(TvShowService::class.java)
    }

    @Provides
    fun provideWatchProviderService(retrofit: Retrofit): WatchProviderService {
        return retrofit.create(WatchProviderService::class.java)
    }
}
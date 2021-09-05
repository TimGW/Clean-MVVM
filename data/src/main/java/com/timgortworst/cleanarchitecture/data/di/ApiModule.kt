package com.timgortworst.cleanarchitecture.data.di

import com.squareup.moshi.Moshi
import com.timgortworst.cleanarchitecture.data.remote.MovieService
import com.timgortworst.cleanarchitecture.data.remote.WatchProviderService
import com.timgortworst.cleanarchitecture.data.remote.jsonAdapter.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @MoshiNetwork
    fun provideMoshi(builder: Moshi.Builder): Moshi {
        return builder
            .add(MoviesJsonAdapter())
            .add(MovieDetailsJsonAdapter())
            .add(WatchProviderJsonAdapter())
            .add(WatchProviderRegionJsonAdapter())
            .add(MovieCreditsJsonAdapter())
            .build()
    }

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    fun provideWatchProviderService(retrofit: Retrofit): WatchProviderService {
        return retrofit.create(WatchProviderService::class.java)
    }
}
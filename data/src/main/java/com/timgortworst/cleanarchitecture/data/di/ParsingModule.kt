package com.timgortworst.cleanarchitecture.data.di

import com.squareup.moshi.Moshi
import com.timgortworst.cleanarchitecture.data.remote.jsonAdapter.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ParsingModule {

    @Provides
    fun provideMoshiBuilder(): Moshi.Builder {
        return Moshi.Builder()
    }

    @Provides
    fun provideNetworkMoshi(builder: Moshi.Builder): Moshi {
        return builder
            .add(MovieJsonAdapter())
            .add(MovieDetailsJsonAdapter())
            .add(TvShowJsonAdapter())
            .add(TvShowDetailsJsonAdapter())
            .add(WatchProviderJsonAdapter())
            .add(WatchProviderRegionJsonAdapter())
            .build()
    }
}
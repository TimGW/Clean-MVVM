package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.network.MovieService
import com.timgortworst.cleanarchitecture.data.repository.MoviePagingSource
import com.timgortworst.cleanarchitecture.data.repository.MovieRepositoryImpl
import com.timgortworst.cleanarchitecture.data.repository.WatchProviderRepositoryImpl
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import com.timgortworst.cleanarchitecture.domain.repository.WatchProviderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindWatchProviderRepository(
        watchProviderRepositoryImpl: WatchProviderRepositoryImpl
    ): WatchProviderRepository

    companion object {

        @Provides
        fun provideMoviePagingSource(
            movieService: MovieService
        ) = MoviePagingSource(movieService)
    }
}

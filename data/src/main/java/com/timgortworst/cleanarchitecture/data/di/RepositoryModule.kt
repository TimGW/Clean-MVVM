package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.repository.MovieRepositoryImpl
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
}
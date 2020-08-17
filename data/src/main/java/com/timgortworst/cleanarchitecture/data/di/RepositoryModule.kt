package com.timgortworst.cleanarchitecture.data.di

import com.timgortworst.cleanarchitecture.data.error.ErrorHandlerImpl
import com.timgortworst.cleanarchitecture.data.repository.MovieRepositoryImpl
import com.timgortworst.cleanarchitecture.domain.model.response.ErrorHandler
import com.timgortworst.cleanarchitecture.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ErrorHandler> { ErrorHandlerImpl() }

    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
}
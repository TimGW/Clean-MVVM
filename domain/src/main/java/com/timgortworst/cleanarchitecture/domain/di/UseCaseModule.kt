package com.timgortworst.cleanarchitecture.domain.di

import com.timgortworst.cleanarchitecture.domain.usecase.movie.*
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetMoviesUseCase> { GetMoviesUseCaseImpl(get(), get()) }
    factory<GetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
}

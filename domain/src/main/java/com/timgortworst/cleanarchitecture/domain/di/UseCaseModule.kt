package com.timgortworst.cleanarchitecture.domain.di

import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movielist.GetMoviesUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetMoviesUseCase> { GetMoviesUseCaseImpl(get(), get()) }
    factory<GetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
}

package com.timgortworst.cleanarchitecture.presentation.di

import com.timgortworst.cleanarchitecture.presentation.features.movie.viewmodel.MovieDetailViewModel
import com.timgortworst.cleanarchitecture.presentation.features.movie.viewmodel.MovieListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}

package com.timgortworst.cleanarchitecture.presentation.features.movie

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetMoviesUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.movie.GetRelatedMoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGetMoviesPagedUseCase(
        getMoviesUseCase: GetMoviesUseCase
    ): UseCase<Unit, Flow<PagingData<Movie>>>

    @Binds
    @ViewModelScoped
    abstract fun provideGetRelatedMoviesPagedUseCase(
        getRelatedMoviesUseCase: GetRelatedMoviesUseCase
    ): UseCase<GetRelatedMoviesUseCase.Params, Flow<Result<List<Movie>>>>

    @Binds
    @ViewModelScoped
    abstract fun provideGetMovieDetailsUseCase(
        getMovieDetailsUseCase: GetMovieDetailsUseCase
    ): UseCase<GetMovieDetailsUseCase.Params, Flow<Result<MovieDetails?>>>
}
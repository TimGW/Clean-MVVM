package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.recyclerview.widget.ConcatAdapter
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.features.base.AdapterItemBinder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class MovieDetailsModule {

    @Binds
    abstract fun providesAdapterBinder(
        movieDetailsAdapterBinder: MovieDetailsAdapterBinder
    ): AdapterItemBinder<ConcatAdapter, MovieDetails>
}
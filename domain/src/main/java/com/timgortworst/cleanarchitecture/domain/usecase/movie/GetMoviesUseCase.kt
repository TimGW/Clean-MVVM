package com.timgortworst.cleanarchitecture.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase

interface GetMoviesUseCase : FlowUseCase<Unit, PagingData<Movie>>
package com.timgortworst.cleanarchitecture.domain.usecase.movie

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase

interface GetMoviesUseCase : FlowUseCase<Unit, PagingData<Movie>>
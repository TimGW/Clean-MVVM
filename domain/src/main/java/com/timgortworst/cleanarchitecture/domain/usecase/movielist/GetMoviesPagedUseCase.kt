package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import androidx.paging.PagingData
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetMoviesPagedUseCase : FlowUseCase<Unit, PagingData<Movie>>
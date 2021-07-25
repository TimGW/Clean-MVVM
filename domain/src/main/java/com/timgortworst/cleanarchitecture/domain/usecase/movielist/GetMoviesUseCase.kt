package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetMoviesUseCase : SuspendUseCase<Unit, Resource<List<Movie>>>
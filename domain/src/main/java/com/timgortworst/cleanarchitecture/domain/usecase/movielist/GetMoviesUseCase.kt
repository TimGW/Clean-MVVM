package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase

interface GetMoviesUseCase : FlowUseCase<Unit, List<Movie>>
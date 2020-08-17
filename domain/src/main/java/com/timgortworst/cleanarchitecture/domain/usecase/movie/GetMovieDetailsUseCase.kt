package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.response.State
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetMovieDetailsUseCase : SuspendUseCase<GetMovieDetailsUseCaseImpl.Params, State<MovieDetails>>

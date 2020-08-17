package com.timgortworst.cleanarchitecture.domain.usecase.moviedetail

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.State
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetMovieDetailsUseCase : SuspendUseCase<GetMovieDetailsUseCaseImpl.Params, State<MovieDetails>>

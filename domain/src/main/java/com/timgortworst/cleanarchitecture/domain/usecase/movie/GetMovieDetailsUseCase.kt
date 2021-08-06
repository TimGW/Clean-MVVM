package com.timgortworst.cleanarchitecture.domain.usecase.movie

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase

interface GetMovieDetailsUseCase : FlowUseCase<GetMovieDetailsUseCaseImpl.Params, Result<MovieDetails?>>

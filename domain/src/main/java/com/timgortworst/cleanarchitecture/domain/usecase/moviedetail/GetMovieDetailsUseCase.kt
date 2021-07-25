package com.timgortworst.cleanarchitecture.domain.usecase.moviedetail

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.usecase.FlowUseCase

interface GetMovieDetailsUseCase : FlowUseCase<GetMovieDetailsUseCaseImpl.Params, MovieDetails>

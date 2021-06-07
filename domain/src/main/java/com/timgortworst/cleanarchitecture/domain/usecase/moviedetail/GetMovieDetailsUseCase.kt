package com.timgortworst.cleanarchitecture.domain.usecase.moviedetail

import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.SuspendUseCase

interface GetMovieDetailsUseCase : SuspendUseCase<GetMovieDetailsUseCaseImpl.Params, Resource<MovieDetails>>

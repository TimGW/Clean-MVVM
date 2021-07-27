package com.timgortworst.cleanarchitecture.domain.usecase.movielist

import androidx.paging.PagingSource
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.usecase.UseCase

interface GetMoviesPagedUseCase : UseCase<Unit, PagingSource<Int, Movie>>
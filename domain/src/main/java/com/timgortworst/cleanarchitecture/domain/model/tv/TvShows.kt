package com.timgortworst.cleanarchitecture.domain.model.tv

data class TvShows(
    val page: Int,
    val results: List<TvShow>,
    val totalPages: Int,
    val totalResults: Int,
)

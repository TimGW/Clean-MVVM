package com.timgortworst.cleanarchitecture.domain.model.movie

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int,
)

package com.timgortworst.cleanarchitecture.domain.model.movie

data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
) {
    companion object {
        const val LOW_RES_PREFIX = "https://image.tmdb.org/t/p/w185/"
        const val HIGH_RES_PREFIX = "https://image.tmdb.org/t/p/w500/"
    }
}
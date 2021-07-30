package com.timgortworst.cleanarchitecture.domain.model.movie

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val watchProviders: String?,
) {

    data class Genre(
        val id: Int,
        val name: String,
    )
}
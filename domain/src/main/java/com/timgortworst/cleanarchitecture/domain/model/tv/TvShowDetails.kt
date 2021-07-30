package com.timgortworst.cleanarchitecture.domain.model.tv

data class TvShowDetails(
    val id: Int = 0,
    val backdropPath: String,
    val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val inProduction: Boolean,
    val lastAirDate: String,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,
    val watchProviders: String,
) {

    data class Genre(
        val id: Int,
        val name: String
    )
}
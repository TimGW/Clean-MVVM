package com.timgortworst.cleanarchitecture.domain.model.movie

data class MovieDetails(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val budget: Int = 0,
    val genres: List<Genre> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val imdbId: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompany> = listOf(),
    val productionCountries: List<ProductionCountry> = listOf(),
    val releaseDate: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val spokenLanguages: List<SpokenLanguage> = listOf(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
) {

    data class Genre(
        val id: Int = 0,
        val name: String = ""
    )

    data class ProductionCompany(
        val id: Int = 0,
        val logoPath: String = "",
        val name: String = "",
        val originCountry: String = ""
    )

    data class ProductionCountry(
        val iso31661: String = "",
        val name: String = ""
    )

    data class SpokenLanguage(
        val iso6391: String = "",
        val name: String = ""
    )
}
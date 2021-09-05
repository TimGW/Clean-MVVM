package com.timgortworst.cleanarchitecture.domain.model.movie

data class MovieDetails(
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    var watchProviders: Map<String, Provider>,
    val modifiedAt: Long,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val status: String
) {

    data class Genre(
        val id: Int,
        val name: String,
    )

    data class Provider(
        val flatRate: List<String>?,
        val buy: List<String>?,
        val rent: List<String>?,
    )
}
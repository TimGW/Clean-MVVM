package com.timgortworst.cleanarchitecture.domain.model.tv

data class TvShowDetails(
    val id: Int = 0,
    val firstAirDate: String,
    val genres: List<Genre>,
    val name: String,
    val overview: String,
    val posterPath: String?,
    var watchProviders: Map<String, Provider>,
    val modifiedAt: Long,
) {

    data class Genre(
        val id: Int,
        val name: String
    )

    data class Provider(
        val flatRate: List<String>?,
        val buy: List<String>?,
        val rent: List<String>?,
    )
}
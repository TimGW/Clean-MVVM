package com.timgortworst.cleanarchitecture.domain.model.tv

import java.util.*

data class TvShow(
    val posterPath: String,
    val popularity: Double,
    val id: Int,
    val backdropPath: String,
    val voteAverage: Double,
    val overview: String,
    val firstAirDate: Date?,
    val originCountry: List<String>,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val voteCount: Int,
    val name: String,
    val originalName: String,
    var lowResImage: String = "",
    var highResImage: String = "",
)
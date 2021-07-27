package com.timgortworst.cleanarchitecture.domain.model.movie

import java.util.*

data class Movie(
    val adult: Boolean = false,
    val backdropPath: String? = "",
    val id: Int = 0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String? = null,
    val releaseDate: Date? = null,
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    var lowResImage: String = "",
    var highResImage: String = "",
)
package com.timgortworst.cleanarchitecture.data.model.movie

import com.squareup.moshi.Json


data class NetworkMovies(
    @field:Json(name = "page") val page: Int?,
    @field:Json(name = "total_pages") val totalPages: Int?,
    @field:Json(name = "total_results") val totalResults: Int?,
    @field:Json(name = "results") val results: List<Result>?,
) {

    data class Result(
        @field:Json(name = "adult") val adult: Boolean?,
        @field:Json(name = "backdrop_path") val backdropPath: String?,
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "original_language") val originalLanguage: String?,
        @field:Json(name = "original_title") val originalTitle: String?,
        @field:Json(name = "overview") val overview: String?,
        @field:Json(name = "popularity") val popularity: Double?,
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "release_date") val releaseDate: String?,
        @field:Json(name = "title") val title: String?,
        @field:Json(name = "video") val video: Boolean?,
        @field:Json(name = "vote_average") val voteAverage: Double?,
        @field:Json(name = "vote_count") val voteCount: Int?,
    )
}
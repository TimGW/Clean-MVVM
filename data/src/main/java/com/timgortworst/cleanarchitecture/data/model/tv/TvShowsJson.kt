package com.timgortworst.cleanarchitecture.data.model.tv

import com.squareup.moshi.Json

data class TvShowsJson(
    @field:Json(name = "page") val page: Int?,
    @field:Json(name = "results") val results: List<Result>?,
    @field:Json(name = "total_pages") val totalPages: Int?,
    @field:Json(name = "total_results") val totalResults: Int?,
) {

    data class Result(
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "popularity") val popularity: Double?,
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "backdrop_path") val backdropPath: String?,
        @field:Json(name = "vote_average") val voteAverage: Double?,
        @field:Json(name = "overview") val overview: String?,
        @field:Json(name = "first_air_date") val firstAirDate: String?,
        @field:Json(name = "origin_country") val originCountry: List<String>?,
        @field:Json(name = "genre_ids") val genreIds: List<Int>?,
        @field:Json(name = "original_language") val originalLanguage: String?,
        @field:Json(name = "vote_count") val voteCount: Int?,
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "original_name") val originalName: String?,
    )
}
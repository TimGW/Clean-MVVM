package com.timgortworst.cleanarchitecture.data.model.tv

import com.google.gson.annotations.SerializedName

data class NetworkTvShows(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val results: ArrayList<Result> = arrayListOf(),
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
) {

    data class Result(
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("id") val id: Int,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("overview") val overview: String,
        @SerializedName("first_air_date") val firstAirDate: String?,
        @SerializedName("origin_country") val originCountry: List<String>,
        @SerializedName("genre_ids") val genreIds: List<Int>,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("name") val name: String,
        @SerializedName("original_name") val originalName: String
    )
}
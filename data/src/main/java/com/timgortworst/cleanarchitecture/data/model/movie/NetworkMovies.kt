package com.timgortworst.cleanarchitecture.data.model.movie

import com.google.gson.annotations.SerializedName

data class NetworkMovies(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val results: ArrayList<Result> = arrayListOf(),
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
) {

    data class Result(
        @SerializedName("adult") val adult: Boolean = false,
        @SerializedName("backdrop_path") val backdropPath: String? = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("original_language") val originalLanguage: String = "",
        @SerializedName("original_title") val originalTitle: String = "",
        @SerializedName("overview") val overview: String = "",
        @SerializedName("popularity") val popularity: Double = 0.0,
        @SerializedName("poster_path") val posterPath: String? = null,
        @SerializedName("release_date") val releaseDate: String = "",
        @SerializedName("title") val title: String = "",
        @SerializedName("video") val video: Boolean = false,
        @SerializedName("vote_average") val voteAverage: Double = 0.0,
        @SerializedName("vote_count") val voteCount: Int = 0
    )
}
package com.timgortworst.cleanarchitecture.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbMovieDetails(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "adult") val adult: Boolean = false,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String = "",
    @ColumnInfo(name = "original_language") val originalLanguage: String = "",
    @ColumnInfo(name = "original_title") val originalTitle: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "popularity") val popularity: Double = 0.0,
    @ColumnInfo(name = "poster_path") val posterPath: String? = null,
    @ColumnInfo(name = "release_date") val releaseDate: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "video") val video: Boolean = false,
    @ColumnInfo(name = "vote_average") val voteAverage: Double = 0.0,
    @ColumnInfo(name = "vote_count") val voteCount: Int = 0,
    @ColumnInfo(name = "budget") val budget: Int = 0,
    @ColumnInfo(name = "genres") val genres: List<Genre> = listOf(),
    @ColumnInfo(name = "homepage") val homepage: String = "",
    @ColumnInfo(name = "imdb_id") val imdbId: String = "",
    @ColumnInfo(name = "revenue") val revenue: Int = 0,
    @ColumnInfo(name = "runtime") val runtime: Int = 0,
    @ColumnInfo(name = "status") val status: String = "",
    @ColumnInfo(name = "tagline") val tagline: String = "",
    @ColumnInfo(name = "watch_providers") val watchProviders: String = "",
) {
    data class Genre(
        @ColumnInfo(name = "id") val id: Int = 0,
        @ColumnInfo(name = "name") val name: String = ""
    )
}
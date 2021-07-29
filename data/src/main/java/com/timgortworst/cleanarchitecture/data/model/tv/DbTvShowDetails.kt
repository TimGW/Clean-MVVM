package com.timgortworst.cleanarchitecture.data.model.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbTvShowDetails(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "first_air_date") val firstAirDate: String,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    @ColumnInfo(name = "homepage") val homepage: String,
    @ColumnInfo(name = "in_production") val inProduction: Boolean,
    @ColumnInfo(name = "last_air_date") val lastAirDate: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "number_of_episodes") val numberOfEpisodes: Int,
    @ColumnInfo(name = "number_of_seasons") val numberOfSeasons: Int,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_name") val originalName: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "watch_providers") val watchProviders: String = "",
) {
    data class Genre(
        @ColumnInfo(name = "id") val id: Int = 0,
        @ColumnInfo(name = "name") val name: String = ""
    )
}
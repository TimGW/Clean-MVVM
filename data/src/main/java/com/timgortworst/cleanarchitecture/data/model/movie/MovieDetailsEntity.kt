package com.timgortworst.cleanarchitecture.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails

@Entity
data class MovieDetailsEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "budget") val budget: Int,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    @ColumnInfo(name = "homepage") val homepage: String,
    @ColumnInfo(name = "imdb_id") val imdbId: String,
    @ColumnInfo(name = "revenue") val revenue: Int,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "watch_providers") val watchProviders: String,
) {
    data class Genre(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String,
    )

    companion object {
        fun from(movieDetails: MovieDetails) = with(movieDetails) {
            MovieDetailsEntity(
                id,
                adult,
                backdropPath,
                originalLanguage,
                originalTitle,
                overview,
                popularity,
                posterPath,
                releaseDate,
                title,
                video,
                voteAverage,
                voteCount,
                budget,
                genres.map { Genre(it.id, it.name) },
                homepage,
                imdbId,
                revenue,
                runtime,
                status,
                tagline,
                watchProviders.orEmpty(),
            )
        }
    }

    fun toMovieDetails() = MovieDetails(
        adult,
        backdropPath,
        budget,
        genres.map { MovieDetails.Genre(it.id, it.name) },
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        revenue,
        runtime,
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount,
        watchProviders,
    )
}
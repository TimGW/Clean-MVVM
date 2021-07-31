package com.timgortworst.cleanarchitecture.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails

@Entity
data class MovieDetailsEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    // key: countrycode, value: WatchProvider
    @ColumnInfo(name = "watch_providers") val watchProviders: Map<String, Provider>?,
) {
    data class Genre(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String,
    )

    data class Provider(
        @ColumnInfo(name = "flat_rate") val flatRate: List<String>?,
        @ColumnInfo(name = "buy") val buy: List<String>?,
        @ColumnInfo(name = "rent") val rent: List<String>?,
    )

    companion object {
        fun from(movieDetails: MovieDetails) = with(movieDetails) {
            MovieDetailsEntity(
                id,
                overview,
                posterPath,
                releaseDate,
                title,
                genres.map {
                    Genre(it.id, it.name)
                },
                watchProviders.mapValues {
                    Provider(
                        it.value.flatRate,
                        it.value.buy,
                        it.value.rent,
                    )
                },
            )
        }
    }

    fun toMovieDetails() = MovieDetails(
        genres.map {
            MovieDetails.Genre(it.id, it.name)
        },
        id,
        overview,
        posterPath,
        releaseDate,
        title,
        watchProviders?.mapValues {
            MovieDetails.Provider(
                it.value.flatRate,
                it.value.buy,
                it.value.rent,
            )
        } ?: emptyMap(),
    )
}
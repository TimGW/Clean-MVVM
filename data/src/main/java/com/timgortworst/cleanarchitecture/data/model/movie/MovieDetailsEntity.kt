package com.timgortworst.cleanarchitecture.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie as DomainMovie

@Entity
data class MovieDetailsEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    @ColumnInfo(name = "watch_providers") val watchProviders: Map<String, Provider>?,
    @ColumnInfo(name = "modified_at") val modifiedAt: Long,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "recommendations") val recommendations: List<Movie>,
    @ColumnInfo(name = "cast") val cast: List<Cast>,
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

    data class Cast(
        @ColumnInfo(name = "adult") val adult: Boolean,
        @ColumnInfo(name = "gender") val gender: Int?,
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "known_for_department") val knownForDepartment: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "original_name") val originalName: String,
        @ColumnInfo(name = "popularity") val popularity: Double,
        @ColumnInfo(name = "profile_path") val profilePath: String?,
        @ColumnInfo(name = "cast_id") val castId: Int,
        @ColumnInfo(name = "character") val character: String,
        @ColumnInfo(name = "credit_id") val creditId: String,
        @ColumnInfo(name = "order") val order: Int,
    )

    data class Movie(
        @ColumnInfo(name = "adult") val adult: Boolean,
        @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
        @ColumnInfo(name = "id") val id: Int,
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
                modifiedAt,
                popularity,
                voteAverage,
                voteCount,
                status,
                recommendations.map {
                    Movie(
                        it.adult,
                        it.backdropPath,
                        it.id,
                        it.originalLanguage,
                        it.originalTitle,
                        it.overview,
                        it.popularity,
                        it.posterPath,
                        it.releaseDate,
                        it.title,
                        it.video,
                        it.voteAverage,
                        it.voteCount,
                    )
                },
                cast.map {
                    Cast(
                        it.adult,
                        it.gender,
                        it.id,
                        it.knownForDepartment,
                        it.name,
                        it.originalName,
                        it.popularity,
                        it.profilePath,
                        it.castId,
                        it.character,
                        it.creditId,
                        it.order
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
        modifiedAt,
        voteAverage,
        voteCount,
        popularity,
        status,
        recommendations.map {
            DomainMovie(
                it.adult,
                it.backdropPath,
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount,
            )
        },
        cast.map {
            Credits.Cast(
                it.adult,
                it.gender,
                it.id,
                it.knownForDepartment,
                it.name,
                it.originalName,
                it.popularity,
                it.profilePath,
                it.castId,
                it.character,
                it.creditId,
                it.order
            )
        },
    )
}
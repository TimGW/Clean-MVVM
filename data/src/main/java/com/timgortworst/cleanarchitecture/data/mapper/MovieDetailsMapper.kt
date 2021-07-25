package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.DbMovieDetails
import com.timgortworst.cleanarchitecture.data.model.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails

fun DbMovieDetails.asDomainModel() = with(this) {
    MovieDetails(
        adult,
        backdropPath,
        budget,
        genres.map { it.asDomainModel() },
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
        voteCount
    )
}

fun NetworkMovieDetails.asDatabaseModel() = with(this) {
    DbMovieDetails(
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
        genres.map { it.asDatabaseModel() },
        homepage,
        imdbId,
        revenue,
        runtime,
        status,
        tagline,
    )
}

fun DbMovieDetails.Genre.asDomainModel() = with(this) {
    MovieDetails.Genre(
        id,
        name
    )
}

fun NetworkMovieDetails.Genre.asDatabaseModel() = with(this) {
    DbMovieDetails.Genre(id, name)
}

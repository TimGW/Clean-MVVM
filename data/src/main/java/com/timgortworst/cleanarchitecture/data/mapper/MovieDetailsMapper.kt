package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails

fun NetworkMovieDetails.asDomainModel() = with(this) {
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
        productionCompanies.map { it.asDomainModel() },
        productionCountries.map { it.asDomainModel() },
        releaseDate,
        revenue,
        runtime,
        spokenLanguages.map { it.asDomainModel() },
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount
    )
}

fun NetworkMovieDetails.Genre.asDomainModel() = with(this) {
    MovieDetails.Genre(
        id,
        name
    )
}

fun NetworkMovieDetails.ProductionCompany.asDomainModel() = with(this) {
    MovieDetails.ProductionCompany(
        id,
        logoPath,
        name,
        originCountry
    )
}

fun NetworkMovieDetails.ProductionCountry.asDomainModel() = with(this) {
    MovieDetails.ProductionCountry(
        iso31661,
        name
    )
}

fun NetworkMovieDetails.SpokenLanguage.asDomainModel() = with(this) {
    MovieDetails.SpokenLanguage(
        iso6391,
        name
    )
}

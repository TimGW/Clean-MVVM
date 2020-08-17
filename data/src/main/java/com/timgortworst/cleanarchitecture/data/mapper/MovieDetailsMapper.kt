package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails

fun NetworkMovieDetails.asDomainModel() = with(this) {
    MovieDetails(
        adult,
        backdropPath,
        budget,
        emptyList(),
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        emptyList(),
        emptyList(),
        releaseDate,
        revenue,
        runtime,
        emptyList(),
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount
    )
}

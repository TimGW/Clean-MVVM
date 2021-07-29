package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.tv.DbTvShowDetails
import com.timgortworst.cleanarchitecture.data.model.tv.NetworkTvShowDetails
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails

fun DbTvShowDetails.asDomainModel() = with(this) {
    TvShowDetails(
        id,
        backdropPath,
        firstAirDate,
        genres.map { it.asDomainModel() },
        homepage,
        inProduction,
        lastAirDate,
        name,
        numberOfEpisodes,
        numberOfSeasons,
        originalLanguage,
        originalName,
        overview,
        popularity,
        posterPath,
        status,
        tagline,
        type,
        voteAverage,
        voteCount,
        watchProviders,
    )
}

fun NetworkTvShowDetails.asDatabaseModel(region: String?) = with(this) {
    DbTvShowDetails(
        id,
        backdropPath.orEmpty(),
        firstAirDate,
        genres.map { it.asDatabaseModel() },
        homepage,
        inProduction,
        lastAirDate,
        name,
        numberOfEpisodes,
        numberOfSeasons,
        originalLanguage,
        originalName,
        overview,
        popularity,
        posterPath,
        status,
        tagline,
        type,
        voteAverage,
        voteCount,
        watchProviders?.results?.get(region)?.flatRate?.joinToString { it.providerName }.orEmpty(),
    )
}

fun DbTvShowDetails.Genre.asDomainModel() = with(this) {
    TvShowDetails.Genre(
        id,
        name
    )
}

fun NetworkTvShowDetails.Genre.asDatabaseModel() = with(this) {
    DbTvShowDetails.Genre(id, name)
}

package com.timgortworst.cleanarchitecture.data.mapper

import com.timgortworst.cleanarchitecture.data.model.tv.NetworkTvShows
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import java.text.SimpleDateFormat
import java.util.*

fun NetworkTvShows.asDomainModel(): List<TvShow> = with(this) {
    results.map {
        TvShow(
            it.posterPath.orEmpty(),
            it.popularity,
            it.id,
            it.backdropPath.orEmpty(),
            it.voteAverage,
            it.overview,
            if (it.firstAirDate?.isNotBlank() == true) SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).parse(it.firstAirDate) else null,
            it.originCountry,
            it.genreIds,
            it.originalLanguage,
            it.voteCount,
            it.name,
            it.originalName,
            "https://image.tmdb.org/t/p/w185/".plus(it.posterPath),
            "https://image.tmdb.org/t/p/original/".plus(it.posterPath),
        )
    }
}
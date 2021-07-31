package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowsJson
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShows

class TvShowsJsonAdapter {

    @FromJson
    fun fromJson(tvShowsJson: TvShowsJson): TvShows? {
        val results = tvShowsJson.results
        if (results.isNullOrEmpty()) return null
        if (tvShowsJson.page == null) return null
        if (tvShowsJson.totalPages == null) return null
        if (tvShowsJson.totalResults == null) return null

        return TvShows(
            tvShowsJson.page,
            tvShowsJson.results.map {
                TvShow(
                    it.posterPath.orEmpty(),
                    it.popularity ?: 0.0,
                    it.id ?: return null,
                    it.backdropPath.orEmpty(),
                    it.voteAverage ?: 0.0,
                    it.overview ?: return null,
                    it.firstAirDate.orEmpty(),
                    it.originCountry ?: emptyList(),
                    it.genreIds ?: emptyList(),
                    it.originalLanguage.orEmpty(),
                    it.voteCount ?: 0,
                    it.name ?: return null,
                    it.originalName.orEmpty(),
                    "https://image.tmdb.org/t/p/w185/".plus(it.posterPath),
                    "https://image.tmdb.org/t/p/original/".plus(it.posterPath),
                )
            },
            tvShowsJson.totalPages,
            tvShowsJson.totalResults,
        )
    }
}
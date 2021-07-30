package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.tv.NetworkTvShows
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShows
import java.text.SimpleDateFormat
import java.util.*

class TvShowJsonAdapter {

    @FromJson
    fun fromJson(networkTvShows: NetworkTvShows): TvShows? {
        val results = networkTvShows.results
        if (results.isNullOrEmpty()) return null
        if (networkTvShows.page == null) return null
        if (networkTvShows.totalPages == null) return null
        if (networkTvShows.totalResults == null) return null

        return TvShows(
            networkTvShows.page,
            networkTvShows.results.map {
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
            networkTvShows.totalPages,
            networkTvShows.totalResults,
        )
    }
}
package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.timgortworst.cleanarchitecture.data.model.movie.NetworkMovies
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.Movies
import java.text.SimpleDateFormat
import java.util.*

class MovieJsonAdapter {

    @FromJson
    fun fromJson(networkMovies: NetworkMovies?): Movies? {
        val results = networkMovies?.results
        if (results.isNullOrEmpty()) return null
        if (networkMovies.page == null) return null
        if (networkMovies.totalPages == null) return null
        if (networkMovies.totalResults == null) return null

        return Movies(
            networkMovies.page,
            networkMovies.results.map {
                Movie(
                    it.adult ?: false,
                    it.backdropPath,
                    it.id ?: return null,
                    it.originalLanguage.orEmpty(),
                    it.originalTitle.orEmpty(),
                    it.overview ?: return null,
                    it.popularity ?: 0.0,
                    it.posterPath,
                    it.releaseDate.orEmpty(),
                    it.title ?: return null,
                    it.video ?: false,
                    it.voteAverage ?: 0.0,
                    it.voteCount ?: 0,
                    "https://image.tmdb.org/t/p/w185/".plus(it.posterPath),
                    "https://image.tmdb.org/t/p/original/".plus(it.posterPath),
                )
            },
            networkMovies.totalPages,
            networkMovies.totalResults,
        )
    }
}
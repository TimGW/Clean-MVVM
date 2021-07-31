package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.data.model.movie.NetworkMovieDetails
import java.util.*

class MovieDetailsJsonAdapter {

    @FromJson
    fun fromJson(networkMovies: NetworkMovieDetails): MovieDetailsEntity? {
        if (networkMovies.id == null) return null
        if (networkMovies.title == null) return null
        if (networkMovies.overview == null) return null

        return with(networkMovies) {
            MovieDetailsEntity(
                id!!,
                overview!!,
                posterPath,
                releaseDate.orEmpty(),
                title!!,
                genres?.mapNotNull { it.asDbModel() } ?: emptyList(),
                watchProviders?.results?.mapValues { it.value.asDbModel() } ?: emptyMap(),
            )
        }
    }

    private fun NetworkMovieDetails.Genre.asDbModel() = with(this) {
        MovieDetailsEntity.Genre(id ?: return@with null, name ?: return@with null)
    }

    private fun NetworkMovieDetails.WatchProviders.Result.asDbModel() = with(this) {
        MovieDetailsEntity.Provider(
            flatRate?.mapNotNull { it.providerName },
            buy?.mapNotNull { it.providerName },
            rent?.mapNotNull { it.providerName },
        )
    }
}



package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsJson
import java.util.*

class MovieDetailsJsonAdapter {

    @FromJson
    fun fromJson(moviesJson: MovieDetailsJson): MovieDetailsEntity? {
        if (moviesJson.id == null) return null
        if (moviesJson.title == null) return null
        if (moviesJson.overview == null) return null

        return with(moviesJson) {
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

    private fun MovieDetailsJson.Genre.asDbModel() = with(this) {
        MovieDetailsEntity.Genre(id ?: return@with null, name ?: return@with null)
    }

    private fun MovieDetailsJson.WatchProviders.Result.asDbModel() = with(this) {
        MovieDetailsEntity.Provider(
            flatRate?.mapNotNull { it.providerName },
            buy?.mapNotNull { it.providerName },
            rent?.mapNotNull { it.providerName },
        )
    }
}



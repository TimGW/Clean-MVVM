package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsJson
import com.timgortworst.cleanarchitecture.data.model.movie.MovieWatchProviders
import java.util.*

class MovieDetailsJsonAdapter {

    @FromJson
    fun fromJson(moviesJson: MovieDetailsJson): MovieDetailsEntity? {
        if (moviesJson.id == null) return null
        if (moviesJson.title == null) return null
        if (moviesJson.overview == null) return null

        val entity = with(moviesJson) {
            MovieDetailsEntity(
                id!!,
                overview!!,
                posterPath,
                releaseDate.orEmpty(),
                title!!,
                genres?.mapNotNull { it.asDbModel() } ?: emptyList(),
                watchProviders?.results?.mapValues { it.value.asDbModel() }
                    ?.filterValues {
                        it.buy?.isNullOrEmpty() == false ||
                                it.flatRate?.isNullOrEmpty() == false ||
                                it.rent?.isNullOrEmpty() == false
                    } ?: emptyMap(),
                System.currentTimeMillis(),
                popularity ?: 0.0,
                voteAverage ?: 0.0,
                voteCount ?: 0,
                status.orEmpty(),
                recommendations.results?.map {
                    MovieDetailsEntity.Movie(
                        it.adult!!,
                        it.backdropPath!!,
                        it.id!!,
                        it.originalLanguage!!,
                        it.originalTitle!!,
                        it.overview!!,
                        it.popularity!!,
                        it.posterPath!!,
                        it.releaseDate!!,
                        it.title!!,
                        it.video!!,
                        it.voteAverage!!,
                        it.voteCount!!,
                    )
                } ?: emptyList(),
                credits.cast.map {
                    MovieDetailsEntity.Cast(
                        it.adult,
                        it.gender,
                        it.id,
                        it.knownForDepartment,
                        it.name,
                        it.originalName,
                        it.popularity,
                        it.profilePath,
                        it.castId,
                        it.character,
                        it.creditId,
                        it.order
                    )
                }
            )
        }
        return entity
    }

    private fun MovieDetailsJson.Genre.asDbModel() = with(this) {
        MovieDetailsEntity.Genre(id ?: return@with null, name ?: return@with null)
    }

    private fun MovieWatchProviders.Result.asDbModel() = with(this) {
        MovieDetailsEntity.Provider(
            flatRate?.mapNotNull { it.providerName },
            buy?.mapNotNull { it.providerName },
            rent?.mapNotNull { it.providerName },
        )
    }
}



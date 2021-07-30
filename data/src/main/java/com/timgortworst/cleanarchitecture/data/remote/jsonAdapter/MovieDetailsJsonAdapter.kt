package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.movie.NetworkMovieDetails
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import java.util.*

class MovieDetailsJsonAdapter {

    private fun NetworkMovieDetails.Genre.asDomainModel() = with(this) {
        MovieDetails.Genre(id ?: return@with null, name ?: return@with null)
    }

    @FromJson // TODO FIX hardcoded NL
    fun fromJson(networkMovies: NetworkMovieDetails): MovieDetails? {
        if (networkMovies.id == null) return null
        if (networkMovies.title == null) return null
        if (networkMovies.overview == null) return null

        return with(networkMovies) {
            MovieDetails(
                adult ?: false,
                backdropPath.orEmpty(),
                budget ?: 0,
                genres?.mapNotNull { it.asDomainModel() } ?: emptyList(),
                homepage.orEmpty(),
                id ?: 0,
                imdbId.orEmpty(),
                originalLanguage.orEmpty(),
                originalTitle.orEmpty(),
                overview.orEmpty(),
                popularity ?: 0.0,
                posterPath,
                releaseDate.orEmpty(),
                revenue ?: 0,
                runtime ?: 0,
                status.orEmpty(),
                tagline.orEmpty(),
                title.orEmpty(),
                video ?: false,
                voteAverage ?: 0.0,
                voteCount ?: 0,
                watchProviders?.results?.get("NL")?.flatRate?.joinToString {
                    it.providerName.orEmpty()
                }.orEmpty(),
            )
        }
    }
}
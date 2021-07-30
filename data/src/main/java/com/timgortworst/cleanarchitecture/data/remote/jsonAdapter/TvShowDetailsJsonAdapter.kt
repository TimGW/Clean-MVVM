package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.tv.NetworkTvShowDetails
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import java.util.*

class TvShowDetailsJsonAdapter {

    private fun NetworkTvShowDetails.Genre.asDomainModel() = with(this) {
        TvShowDetails.Genre(id ?: return@with null, name ?: return@with null)
    }

    @FromJson // TODO FIX hardcoded NL
    fun fromJson(networkTvShows: NetworkTvShowDetails): TvShowDetails? {
        if (networkTvShows.id == null) return null
        if (networkTvShows.name == null) return null
        if (networkTvShows.overview == null) return null

        return with(networkTvShows) {
            TvShowDetails(
                id!!,
                backdropPath.orEmpty(),
                firstAirDate.orEmpty(),
                genres?.map {
                    TvShowDetails.Genre(it.id ?: return null, it.name ?: return null)
                } ?: emptyList(),
                homepage.orEmpty(),
                inProduction ?: false,
                lastAirDate.orEmpty(),
                name!!,
                numberOfEpisodes ?: 0,
                numberOfSeasons ?: 1,
                originalLanguage.orEmpty(),
                originalName.orEmpty(),
                overview!!,
                popularity ?: 0.0,
                posterPath,
                status.orEmpty(),
                tagline.orEmpty(),
                type.orEmpty(),
                voteAverage ?: 0.0,
                voteCount ?: 0,
                watchProviders?.results?.get("NL")?.flatRate?.joinToString {
                    it.providerName
                }.orEmpty(),
            )
        }
    }
}
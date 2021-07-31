package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.tv.NetworkTvShowDetails
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowDetailsEntity
import java.util.*

class TvShowDetailsJsonAdapter {

    @FromJson
    fun fromJson(networkTvShows: NetworkTvShowDetails): TvShowDetailsEntity? {
        if (networkTvShows.id == null) return null
        if (networkTvShows.name == null) return null
        if (networkTvShows.overview == null) return null

        return with(networkTvShows) {
            TvShowDetailsEntity(
                id!!,
                firstAirDate.orEmpty(),
                genres?.mapNotNull { it.asDbModel() } ?: emptyList(),
                name!!,
                overview!!,
                posterPath,
                watchProviders?.results?.mapValues { it.value.asDbModel() } ?: emptyMap(),
            )
        }
    }

    private fun NetworkTvShowDetails.Genre.asDbModel() = with(this) {
        TvShowDetailsEntity.Genre(id ?: return@with null, name ?: return@with null)
    }

    private fun NetworkTvShowDetails.WatchProviders.Result.asDbModel() = with(this) {
        TvShowDetailsEntity.Provider(
            flatRate?.mapNotNull { it.providerName },
            buy?.mapNotNull { it.providerName },
            rent?.mapNotNull { it.providerName },
        )
    }
}
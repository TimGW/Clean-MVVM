package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowDetailsEntity
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowDetailsJson
import java.util.*

class TvShowDetailsJsonAdapter {

    @FromJson
    fun fromJson(tvShowsJson: TvShowDetailsJson): TvShowDetailsEntity? {
        if (tvShowsJson.id == null) return null
        if (tvShowsJson.name == null) return null
        if (tvShowsJson.overview == null) return null

        return with(tvShowsJson) {
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

    private fun TvShowDetailsJson.Genre.asDbModel() = with(this) {
        TvShowDetailsEntity.Genre(id ?: return@with null, name ?: return@with null)
    }

    private fun TvShowDetailsJson.WatchProviders.Result.asDbModel() = with(this) {
        TvShowDetailsEntity.Provider(
            flatRate?.mapNotNull { it.providerName },
            buy?.mapNotNull { it.providerName },
            rent?.mapNotNull { it.providerName },
        )
    }
}
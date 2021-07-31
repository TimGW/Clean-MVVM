package com.timgortworst.cleanarchitecture.data.model.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails

@Entity
data class TvShowDetailsEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "first_air_date") val firstAirDate: String,
    @ColumnInfo(name = "genres") val genres: List<Genre>,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    // key: countrycode, value: WatchProvider
    @ColumnInfo(name = "watch_providers") val watchProviders: Map<String, Provider>?,
) {
    data class Genre(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String = ""
    )

    data class Provider(
        @ColumnInfo(name = "flat_rate") val flatRate: List<String>?,
        @ColumnInfo(name = "buy") val buy: List<String>?,
        @ColumnInfo(name = "rent") val rent: List<String>?,
    )

    companion object {
        fun from(tvShowDetails: TvShowDetails) = with(tvShowDetails) {
            TvShowDetailsEntity(
                id,
                firstAirDate,
                genres.map { Genre(it.id, it.name) },
                name,
                overview,
                posterPath,
                watchProviders.mapValues {
                    Provider(
                        it.value.flatRate,
                        it.value.buy,
                        it.value.rent,
                    )
                },
            )
        }
    }

    fun toTvShowDetails() = TvShowDetails(
        id,
        firstAirDate,
        genres.map { TvShowDetails.Genre(it.id, it.name) },
        name,
        overview,
        posterPath,
        watchProviders?.mapValues {
            TvShowDetails.Provider(
                it.value.flatRate,
                it.value.buy,
                it.value.rent,
            )
        } ?: emptyMap(),
    )
}
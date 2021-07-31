package com.timgortworst.cleanarchitecture.data.model.tv

import com.squareup.moshi.Json

data class NetworkTvShowDetails(
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "created_by") val createdBy: List<CreatedBy>?,
    @field:Json(name = "episode_run_time") val episodeRunTime: List<Int>?,
    @field:Json(name = "first_air_date") val firstAirDate: String?,
    @field:Json(name = "genres") val genres: List<Genre>?,
    @field:Json(name = "homepage") val homepage: String?,
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "in_production") val inProduction: Boolean?,
    @field:Json(name = "languages") val languages: List<String>?,
    @field:Json(name = "last_air_date") val lastAirDate: String?,
    @field:Json(name = "last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAir?,
    @field:Json(name = "name") val name: String?,
//    @field:Json(name = "nextEpisodeToAir") val nextEpisodeToAir: Any? = null?,
    @field:Json(name = "networks") val networks: List<Network>?,
    @field:Json(name = "number_of_episodes") val numberOfEpisodes: Int?,
    @field:Json(name = "number_of_seasons") val numberOfSeasons: Int?,
    @field:Json(name = "origin_country") val originCountry: List<String>?,
    @field:Json(name = "original_language") val originalLanguage: String?,
    @field:Json(name = "original_name") val originalName: String?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "popularity") val popularity: Double?,
    @field:Json(name = "poster_path") val posterPath: String??,
    @field:Json(name = "production_companies") val productionCompanies: List<Network>?,
    @field:Json(name = "production_countries") val productionCountries: List<ProductionCountry>?,
    @field:Json(name = "seasons") val seasons: List<Season>?,
    @field:Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "tagline") val tagline: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "vote_average") val voteAverage: Double?,
    @field:Json(name = "vote_count") val voteCount: Int?,
    @field:Json(name = "watch/providers") val watchProviders: WatchProviders?,
) {

    data class CreatedBy(
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "credit_id") val creditID: String?,
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "gender") val gender: Int?,
        @field:Json(name = "profile_path") val profilePath: String?
    )

    data class Genre(
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "name") val name: String?,
    )

    data class LastEpisodeToAir(
        @field:Json(name = "air_date") val airDate: String?,
        @field:Json(name = "episode_number") val episodeNumber: Int?,
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "overview") val overview: String?,
        @field:Json(name = "production_code") val productionCode: String?,
        @field:Json(name = "season_number") val seasonNumber: Int?,
        @field:Json(name = "still_path") val stillPath: String?,
        @field:Json(name = "vote_average") val voteAverage: Double?,
        @field:Json(name = "vote_count") val voteCount: Int,
    )

    data class Network(
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "logo_path") val logoPath: String?,
        @field:Json(name = "origin_country") val originCountry: String?,
    )

    data class ProductionCountry(
        @field:Json(name = "iso_3166_1") val iso3166_1: String?,
        @field:Json(name = "name") val name: String?,
    )

    data class Season(
        @field:Json(name = "air_date") val airDate: String?,
        @field:Json(name = "episode_count") val episodeCount: Int?,
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "overview") val overview: String?,
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "season_number") val seasonNumber: Int?,
    )

    data class SpokenLanguage(
        @field:Json(name = "english_name") val englishName: String,
        @field:Json(name = "iso_639_1") val iso639_1: String,
        @field:Json(name = "name") val name: String,
    )

    data class WatchProviders(
        @field:Json(name = "results") val results: Map<String, Result>?
    ) {
        data class Result(
            @field:Json(name = "link") val link: String?,
            @field:Json(name = "flatrate") val flatRate: List<FlatRate>?,
            @field:Json(name = "buy") val buy: List<Buy>?,
            @field:Json(name = "rent") val rent: List<Rent>?,
        ) {
            data class FlatRate(
                @field:Json(name = "display_priority") val displayPriority: Long?,
                @field:Json(name = "logo_path") val logoPath: String?,
                @field:Json(name = "provider_id") val providerID: Long?,
                @field:Json(name = "provider_name") val providerName: String?
            )

            data class Buy(
                @field:Json(name = "display_priority") val displayPriority: Long?,
                @field:Json(name = "logo_path") val logoPath: String?,
                @field:Json(name = "provider_id") val providerID: Long?,
                @field:Json(name = "provider_name") val providerName: String?
            )

            data class Rent(
                @field:Json(name = "display_priority") val displayPriority: Long?,
                @field:Json(name = "logo_path") val logoPath: String?,
                @field:Json(name = "provider_id") val providerID: Long?,
                @field:Json(name = "provider_name") val providerName: String?
            )
        }
    }
}
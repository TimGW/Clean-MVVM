package com.timgortworst.cleanarchitecture.data.model.tv

import com.google.gson.annotations.SerializedName

data class NetworkTvShowDetails(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("created_by") val createdBy: List<CreatedBy>,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("in_production") val inProduction: Boolean,
    @SerializedName("languages") val languages: List<String>,
    @SerializedName("last_air_date") val lastAirDate: String,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAir,
    @SerializedName("name") val name: String,
//    @SerializedName("nextEpisodeToAir") val nextEpisodeToAir: Any? = null,
    @SerializedName("networks") val networks: List<Network>,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<Network>,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>,
    @SerializedName("seasons") val seasons: List<Season>,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("watch/providers") val watchProviders: WatchProviders? = null,
) {

    data class CreatedBy(
        @SerializedName("id") val id: Int,
        @SerializedName("credit_id") val creditID: String,
        @SerializedName("name") val name: String,
        @SerializedName("gender") val gender: Int,
        @SerializedName("profile_path") val profilePath: String?
    )

    data class Genre(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
    )

    data class LastEpisodeToAir(
        @SerializedName("air_date") val airDate: String,
        @SerializedName("episode_number") val episodeNumber: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("production_code") val productionCode: String,
        @SerializedName("season_number") val seasonNumber: Int,
        @SerializedName("still_path") val stillPath: String?,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("vote_count") val voteCount: Int
    )

    data class Network(
        @SerializedName("name") val name: String,
        @SerializedName("id") val id: Int,
        @SerializedName("logo_path") val logoPath: String?,
        @SerializedName("origin_country") val originCountry: String
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1") val iso3166_1: String,
        @SerializedName("name") val name: String,
    )

    data class Season(
        @SerializedName("air_date") val airDate: String,
        @SerializedName("episode_count") val episodeCount: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("season_number") val seasonNumber: Int
    )

    data class SpokenLanguage(
        @SerializedName("english_name") val englishName: String,
        @SerializedName("iso_639_1") val iso639_1: String,
        @SerializedName("name") val name: String,
    )

    data class WatchProviders(
        @SerializedName("results") val results: Map<String, Result>
    ) {
        data class Result(
            @SerializedName("link") val link: String,
            @SerializedName("flatrate") val flatRate: List<FlatRate>
        ) {
            data class FlatRate(
                @SerializedName("display_priority") val displayPriority: Int,
                @SerializedName("logo_path") val logoPath: String?,
                @SerializedName("provider_id") val providerID: Int,
                @SerializedName("provider_name") val providerName: String
            )
        }
    }
}
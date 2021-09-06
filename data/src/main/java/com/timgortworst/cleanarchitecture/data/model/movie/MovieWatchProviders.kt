package com.timgortworst.cleanarchitecture.data.model.movie

import com.squareup.moshi.Json

data class MovieWatchProviders(
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
package com.timgortworst.cleanarchitecture.data.model.watchprovider

import com.squareup.moshi.Json

data class WatchProvidersJson(
    @field:Json(name = "results") val results: List<Result>?,
) {
    data class Result(
        @field:Json(name = "display_priority") val displayPriority: Int?,
        @field:Json(name = "logo_path") val logoPath: String?,
        @field:Json(name = "provider_name") val providerName: String?,
        @field:Json(name = "provider_id") val providerId: Int?,
    )
}
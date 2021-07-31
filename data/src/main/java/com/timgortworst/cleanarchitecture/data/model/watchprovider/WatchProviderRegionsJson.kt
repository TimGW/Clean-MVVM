package com.timgortworst.cleanarchitecture.data.model.watchprovider

import com.squareup.moshi.Json

data class WatchProviderRegionsJson(
    @field:Json(name = "results") val results: List<Result>?,
) {
    data class Result(
        @field:Json(name = "iso_3166_1") val iso: String?,
        @field:Json(name = "english_name") val englishName: String?,
        @field:Json(name = "native_name") val nativeName: String?,
    )
}
package com.timgortworst.cleanarchitecture.data.model.watchproviders

import com.google.gson.annotations.SerializedName

data class NetworkWatchProviderRegions(
    @SerializedName("results")
    val results: ArrayList<Result>,
) {

    data class Result(
        @SerializedName("iso_3166_1")
        val iso: String,
        @SerializedName("english_name")
        val englishName: String,
        @SerializedName("native_name")
        val nativeName: String,
    )
}
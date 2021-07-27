package com.timgortworst.cleanarchitecture.data.model

import com.google.gson.annotations.SerializedName

data class NetworkWatchProviders(
    @SerializedName("results")
    val results: ArrayList<Result>,
) {
    data class Result(
        @SerializedName("display_priority")
        val displayPriority: Int,
        @SerializedName("logo_path")
        val logoPath: String,
        @SerializedName("provider_name")
        val providerName: String,
        @SerializedName("provider_id")
        val providerId: Int,
    )
}
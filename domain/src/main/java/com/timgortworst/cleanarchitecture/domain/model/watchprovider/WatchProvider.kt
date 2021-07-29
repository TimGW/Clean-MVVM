package com.timgortworst.cleanarchitecture.domain.model.watchprovider

data class WatchProvider(
    val displayPriority: Int,
    val logoPath: String,
    val providerName: String,
    val providerId: Int,
)
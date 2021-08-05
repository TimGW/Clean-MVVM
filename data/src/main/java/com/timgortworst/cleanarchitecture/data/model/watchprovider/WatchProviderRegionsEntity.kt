package com.timgortworst.cleanarchitecture.data.model.watchprovider

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timgortworst.cleanarchitecture.domain.model.watchprovider.WatchProviderRegion

@Entity
data class WatchProviderRegionsEntity(
    @PrimaryKey @ColumnInfo(name = "iso") val iso: String,
    @ColumnInfo(name = "english_name") val englishName: String,
    @ColumnInfo(name = "native_name") val nativeName: String,
) {
    companion object {
        fun from(watchProviderRegion: WatchProviderRegion) = with(watchProviderRegion) {
            WatchProviderRegionsEntity(
                iso,
                englishName,
                nativeName
            )
        }
    }

    fun toWatchProviderRegion() = WatchProviderRegion(
        iso,
        englishName,
        nativeName
    )
}
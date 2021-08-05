package com.timgortworst.cleanarchitecture.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface WatchProviderDao {

    @Query("SELECT * FROM WatchProviderRegionsEntity")
    fun getWatchProviderRegions(): Flow<List<WatchProviderRegionsEntity>?>

    /**
     * SQLite database triggers only allow notifications at table level, not at row level.
     * distinctUntilChanged ensures that you only get notified when the row has changed
     */
    fun getWatchProviderRegionsDistinctUntilChanged() =
        getWatchProviderRegions().distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWatchProviderRegions(watchProviderRegionsEntity: List<WatchProviderRegionsEntity>)
}

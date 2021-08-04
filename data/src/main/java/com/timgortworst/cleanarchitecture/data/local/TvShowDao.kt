package com.timgortworst.cleanarchitecture.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowDetailsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShowDetailsEntity WHERE id = :id")
    fun getTvShowDetails(id: Int): Flow<TvShowDetailsEntity?>

    /**
     * SQLite database triggers only allow notifications at table level, not at row level.
     * distinctUntilChanged ensures that you only get notified when the row has changed
     */
    fun getTvShowDetailsDistinctUntilChanged(id: Int) =
        getTvShowDetails(id).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowDetails(tvShowDetailsEntity: TvShowDetailsEntity)
}

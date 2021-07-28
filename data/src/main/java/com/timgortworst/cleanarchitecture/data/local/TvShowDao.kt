package com.timgortworst.cleanarchitecture.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timgortworst.cleanarchitecture.data.model.tv.DbTvShowDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    // TODO convert to single object instead of list
    @Query("SELECT * FROM DbTvShowDetails WHERE id = :id")
    fun getTvShowDetails(id: Int): Flow<List<DbTvShowDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowDetails(dbTvShowDetails: DbTvShowDetails)
}

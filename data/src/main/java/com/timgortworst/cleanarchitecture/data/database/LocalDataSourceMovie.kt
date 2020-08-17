package com.timgortworst.cleanarchitecture.data.database

import androidx.room.*
import com.timgortworst.cleanarchitecture.data.model.DbMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDataSourceMovie {

    @Query("SELECT * FROM DbMovie")
    fun getMovies(): Flow<List<DbMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(dbMovies: List<DbMovie>)
}

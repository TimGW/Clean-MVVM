package com.timgortworst.cleanarchitecture.data.local

import androidx.room.*
import com.timgortworst.cleanarchitecture.data.model.DbMovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // TODO convert to single object instead of list
    @Query("SELECT * FROM DbMovieDetails WHERE id = :movieId")
    fun getMovieDetails(movieId: Int): Flow<List<DbMovieDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(dbMovieDetails: DbMovieDetails)
}

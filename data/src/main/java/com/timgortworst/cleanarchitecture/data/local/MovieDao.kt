package com.timgortworst.cleanarchitecture.data.local

import androidx.room.*
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieDetailsEntity WHERE id = :id")
    fun getMovieDetails(id: Int): Flow<MovieDetailsEntity?>

    /**
     * SQLite database triggers only allow notifications at table level, not at row level.
     * distinctUntilChanged ensures that you only get notified when the row has changed
     */
    fun getMovieDetailsDistinctUntilChanged(id: Int) =
        getMovieDetails(id).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity)
}

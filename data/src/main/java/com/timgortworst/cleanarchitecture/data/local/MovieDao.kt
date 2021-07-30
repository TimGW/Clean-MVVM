package com.timgortworst.cleanarchitecture.data.local

import androidx.room.*
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // TODO convert to single object instead of list
    @Query("SELECT * FROM MovieDetailsEntity WHERE id = :movieId")
    fun getMovieDetails(movieId: Int): Flow<List<MovieDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity)
}

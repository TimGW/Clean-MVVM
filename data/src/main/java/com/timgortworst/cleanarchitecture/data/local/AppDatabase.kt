package com.timgortworst.cleanarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timgortworst.cleanarchitecture.data.model.movie.DbMovieDetails
import com.timgortworst.cleanarchitecture.data.model.tv.DbTvShowDetails

@Database(entities = [DbMovieDetails::class, DbTvShowDetails::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}

package com.timgortworst.cleanarchitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timgortworst.cleanarchitecture.data.model.DbMovie

@Database(entities = [DbMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): LocalDataSourceMovie
}

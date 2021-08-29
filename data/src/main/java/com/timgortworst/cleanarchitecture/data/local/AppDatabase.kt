package com.timgortworst.cleanarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity
import com.timgortworst.cleanarchitecture.data.model.watchprovider.WatchProviderRegionsEntity

@Database(
    entities = [
        MovieDetailsEntity::class,
        WatchProviderRegionsEntity::class,
    ], version = 1
)
@TypeConverters(TypeConverterMovie::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun watchProviderDao(): WatchProviderDao
}

package com.timgortworst.cleanarchitecture.data.di

import androidx.room.Room
import com.timgortworst.cleanarchitecture.data.database.AppDatabase
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().movieDao() }
}
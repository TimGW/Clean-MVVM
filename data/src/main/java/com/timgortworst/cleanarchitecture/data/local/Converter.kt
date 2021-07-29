package com.timgortworst.cleanarchitecture.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.timgortworst.cleanarchitecture.data.model.movie.DbMovieDetails
import com.timgortworst.cleanarchitecture.data.model.tv.DbTvShowDetails
import java.lang.reflect.Type

class Converter {
    @TypeConverter
    fun fromMovieString(value: String): List<DbMovieDetails.Genre> {
        val listType: Type = object : TypeToken<List<DbMovieDetails.Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMovieList(list: List<DbMovieDetails.Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromTvString(value: String): List<DbTvShowDetails.Genre> {
        val listType: Type = object : TypeToken<List<DbTvShowDetails.Genre>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTvList(list: List<DbTvShowDetails.Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
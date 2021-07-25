package com.timgortworst.cleanarchitecture.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.timgortworst.cleanarchitecture.data.model.DbMovieDetails
import java.lang.reflect.Type

class Converter {
    @TypeConverter
    fun fromString(value: String): List<DbMovieDetails.Genre> {
        val listType: Type = object : TypeToken<List<DbMovieDetails.Genre>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<DbMovieDetails.Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}